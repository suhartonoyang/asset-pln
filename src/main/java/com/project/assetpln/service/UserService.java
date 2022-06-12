package com.project.assetpln.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.project.assetpln.model.AesConfiguration;
import com.project.assetpln.model.User;
import com.project.assetpln.repository.UserRepository;
import com.project.assetpln.utils.EncryptorAesGcmPassword;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AesConfigurationService aesConfigurationService;

	@Autowired
	private EncryptorAesGcmPassword encryptor;

	@Value("${password.key.aes}")
	private String PASSWORD_KEY;

	private final Charset UTF_8 = StandardCharsets.UTF_8;

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User register(User user) throws Exception {
		User newUser = new User();
		User existingUser = getUserById(user.getId());
		if (existingUser == null) {
			newUser.setCreatedBy(user.getCreatedBy());
			newUser.setCreatedDate(new Date());
		} else {
			newUser.setId(existingUser.getId());
			newUser.setCreatedBy(existingUser.getCreatedBy());
			newUser.setCreatedDate(existingUser.getCreatedDate());
		}

		AesConfiguration aesConfiguration = aesConfigurationService.getAesConfigurationById(1);
		String encryptedPassword = encryptor.encrypt(user.getPassword().getBytes(UTF_8), PASSWORD_KEY, aesConfiguration);

		newUser.setUsername(user.getUsername());
		newUser.setPassword(encryptedPassword);
		newUser.setPasswordKey(PASSWORD_KEY);
		newUser.setAesConfiguration(aesConfiguration);
		newUser.setRole(user.getRole().toUpperCase());
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		newUser.setHandphoneNumber(user.getHandphoneNumber());

		return userRepository.save(newUser);
	}

	public User login(String username, String password) throws Exception {
		User user = getUserByUsername(username);
		if (user == null)
			return null;

		if (!PASSWORD_KEY.equals(user.getPasswordKey()))
			throw new Exception("Password Key in server and database are not match");

		AesConfiguration aesConfiguration = user.getAesConfiguration();
		String decryptedText = encryptor.decrypt(user.getPassword(), PASSWORD_KEY, aesConfiguration);

		if (decryptedText == null || decryptedText.equals(""))
			throw new Exception("Result of decrypt password is empty");
		else if (!decryptedText.equals(password))
			return null;

		return user;
	}

	public List<User> getAllTeachers() {
		return userRepository.findByRole("teacher");
	}

	public User getUserById(Integer id) {
		return userRepository.findById(id).orElse(null);
	}

	public void deleteUserById(Integer id) {
		userRepository.deleteById(id);
	}

}
