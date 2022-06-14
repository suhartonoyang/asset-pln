package com.project.assetpln.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;
import com.project.assetpln.model.File;
import com.project.assetpln.model.Flooding;
import com.project.assetpln.model.GarduInduk;
import com.project.assetpln.model.Location;
import com.project.assetpln.model.UploadHistory;
import com.project.assetpln.repository.FileRepository;
import com.project.assetpln.repository.FloodingRepository;
import com.project.assetpln.repository.GarduIndukRepository;
import com.project.assetpln.repository.LocationRepository;
import com.project.assetpln.repository.UploadHistoryRepository;
import com.project.assetpln.utils.EncryptorAesGcm;

@Service
public class UploadHistoryService {

	public ImmutableMap<String, Integer> YA_TIDAK_MAP = ImmutableMap.<String, Integer>builder().put("YA", 1).put("TIDAK", 0).build();

	@Autowired
	private UploadHistoryRepository uploadHistoryRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private GarduIndukRepository garduIndukRepository;

	@Autowired
	private FloodingRepository floodingRepository;

	@Autowired
	private FileHandlingService fileHandlingService;

	@Autowired
	private EncryptorAesGcm encryptorFile;

	@Value("${password.key.aes}")
	private String PASSWORD_KEY;

	@Value("${upload.path}")
	private String uploadPath;

	public List<UploadHistory> getUploadHistoriesByCriteria(Integer id, Date uploadDate, Integer locationId) {
		return uploadHistoryRepository.findByCriteria(id, uploadDate, locationId);
	}

	public UploadHistory getOneById(Integer id) {
		return uploadHistoryRepository.findById(id).orElse(null);
	}

	@Transactional
	public UploadHistory upload(String username, Integer locationId, MultipartFile file) throws Exception {
		UploadHistory uploadHistory = new UploadHistory();

		Location location = locationRepository.findById(locationId).orElse(null);
		if (location != null) {
			uploadHistory.setLocation(location);
		}

		Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		uploadHistory.setCreatedBy(username);
		uploadHistory.setCreatedDate(now);
		uploadHistory.setUploadDate(now);

		if (!file.isEmpty()) {
			File newFile = new File();
			newFile.setCreatedBy(username);
			newFile.setCreatedDate(now);
			newFile.setData(file.getBytes());
			newFile.setType(file.getContentType());
			newFile.setName(file.getOriginalFilename());

			fileRepository.save(newFile);

			uploadHistory.setFile(newFile);
		}

		uploadHistoryRepository.save(uploadHistory);

		boolean extractResult = false;
		if (uploadHistory.getId() != null) {
			extractResult = extractFileIntoDB(username, location, file);
		}

		if (extractResult) {
			fileHandlingService.saveMultipartFile("asset-pln", file);
			String filename = StringUtils.cleanPath(file.getOriginalFilename());

			String encryptedPath = fileHandlingService.getPath("asset-pln", (filename + ".encrypted"));
			java.io.File encryptedFile = new java.io.File(encryptedPath);
			encryptorFile.encrypt(getFile(fileHandlingService.getPath("asset-pln", filename)), encryptedFile);

			String decryptedPath = fileHandlingService.getPath("asset-pln", filename.replace(".", "-decrypted."));
			java.io.File decryptedFile = new java.io.File(decryptedPath);
			encryptorFile.decrypt(encryptedFile, decryptedFile);
		}

		return uploadHistory;
	}

	private boolean extractFileIntoDB(String username, Location location, MultipartFile file) throws IOException {
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();

		List<Flooding> floodings = new ArrayList<Flooding>();

		while (rows.hasNext()) {
			Row currentRow = rows.next();
			Iterator<Cell> cells = currentRow.iterator();

			Flooding flooding = new Flooding();
			if (currentRow.getRowNum() <= 1) {
				continue;
			}

			int cell = 1;
			while (cells.hasNext()) {
				Cell currentCell = cells.next();
				if (currentCell.getColumnIndex() == 0) {
					continue;
				}

				switch (cell) {
				case 1:
					flooding.setGarduIndukName(currentCell.getStringCellValue().toUpperCase());
					break;
				case 2:
					flooding.setPenyulang(currentCell.getStringCellValue().toUpperCase());
					break;
				case 3:
					flooding.setGarduNo(currentCell.getStringCellValue());
					break;
				case 4:
					flooding.setDisasterDate((Date) currentCell.getDateCellValue());
					break;
				case 5:
					String garduSubmerged = currentCell.getStringCellValue();
					if (garduSubmerged == null || garduSubmerged.equals("")) {
						garduSubmerged = "TIDAK";
					} else {
						garduSubmerged = garduSubmerged.toUpperCase();
					}
					flooding.setIsGarduSubmerged(YA_TIDAK_MAP.get(garduSubmerged));
					break;
				case 6:
					String neighbourhoodSubmerged = currentCell.getStringCellValue().toUpperCase();
					if (neighbourhoodSubmerged == null || neighbourhoodSubmerged.equals("")) {
						neighbourhoodSubmerged = "TIDAK";
					}
					flooding.setIsNeighbourhoodSubmerged(YA_TIDAK_MAP.get(neighbourhoodSubmerged));
					break;
				default:
					break;
				}

				cell++;
			}

			flooding.setCreatedBy(username);
			flooding.setCreatedDate(new Date());
			flooding.setLocation(location);
			floodings.add(flooding);
		}

		workbook.close();

		// proses delete
		List<Date> disasterDates = floodings.stream().map(m -> m.getDisasterDate()).distinct().collect(Collectors.toList());
		for (Date disasterDate : disasterDates) {
			List<Flooding> floodingDeletes = floodingRepository.findByLocation_IdAndDisasterDate(location.getId(), disasterDate);
			if (!floodingDeletes.isEmpty()) {
				floodingRepository.deleteAll(floodingDeletes);
			}
		}

		// get master of gardu induk
		List<String> garduIndukNames = floodings.stream().map(m -> m.getGarduIndukName()).distinct().collect(Collectors.toList());
		List<GarduInduk> garduInduks = new ArrayList<GarduInduk>();

		for (String gin : garduIndukNames) {
			GarduInduk garduInduk = garduIndukRepository.findByName(gin);
			if (garduInduk != null) {
				garduInduks.add(garduInduk);
			}
		}

		// proses insert
		floodings.stream().forEach(p -> {
			GarduInduk garduInduk = garduInduks.stream().filter(f -> f.getName().equalsIgnoreCase(p.getGarduIndukName())).findFirst()
					.orElse(null);
			p.setGarduInduk(garduInduk);
		});

		floodingRepository.saveAll(floodings);

		return true;
	}

	private java.io.File getFile(String path) throws FileNotFoundException {
		java.io.File file = ResourceUtils.getFile(path);
		return file;
	}
}
