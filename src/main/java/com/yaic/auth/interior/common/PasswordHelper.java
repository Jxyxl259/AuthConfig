package com.yaic.auth.interior.common;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.yaic.auth.interior.model.AppUserModel;


@Service
public class PasswordHelper {

    @SuppressWarnings( "unused" )
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String HASH_ALGORITHM_NAME = "SHA-256";
    private int HASH_ITERATIONS = 2;
    private static final String SHA_SALT = "cms" ;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName(String algorithmName) {
        this.HASH_ALGORITHM_NAME = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.HASH_ITERATIONS = hashIterations;
    }

    public void encryptPassword(AppUserModel userDto) {

        String newPassword = new SimpleHash(
                HASH_ALGORITHM_NAME,
                userDto.getPassword(),
                getSalt(userDto),
                HASH_ITERATIONS).toHex();

        userDto.setPassword(newPassword);
    }
    
    /**
     * 获取用户密码加密后字符串
     * （用于匹配密码是否相等）
     * <p>Author:lujicong
     * <p>Date: 2015-12-10
     * <p>Version: 1.0
     * @param userDefDto
     * @return
     */
    public String getEncryptPassword(AppUserModel userDto) {

        String newPassword = new SimpleHash(
                HASH_ALGORITHM_NAME,
                userDto.getPassword(),
                getSalt(userDto),
                HASH_ITERATIONS).toHex();

        return newPassword;
    }

	public ByteSource getSalt(AppUserModel user) {
		final String salt = new StringBuilder(user.getUserCode()).append(SHA_SALT).toString();
		return ByteSource.Util.bytes(salt);
	}
	
//	public static void main(String[] agrs) {
//		// 加密
//		AppUserModel userDto = new AppUserModel();
//		userDto.setUserCode("001");
//		userDto.setPassword("001");
//		String newPassword = new SimpleHash("SHA1", userDto.getPassword(),
//				ByteSource.Util.bytes(userDto.getUserCode() + SHA_SALT), 2).toHex();
//		System.out.println(newPassword);
//		
//		// 匹配
//		String newPasswordOld = new SimpleHash("SHA1", "1", ByteSource.Util.bytes(userDto.getUserCode() + SHA_SALT), 2)
//				.toHex();
//		System.out.println(newPasswordOld);
//	}
}
