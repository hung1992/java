/**
 * Copyright by Technologic Arts
 * Project: e-Test
 * Package: utils
 * Author: ta-08
 */
package utils;

/**
 * This is class Constant.java
 */
public class Constant {
	public final static String CAN_NOT_PARSE_JSON_OBJECT = "Can not parse data.";
	public final static String CAN_NOT_CREATE_USER = "Can not register candidate.";
	public final static String EMAIL_IS_EXIST = "Email has already exited.";
	public final static String EMAIL_IS_NOT_EXIST = "Email is not exited.";
	public final static String CAN_NOT_CHANGE_PASS = "Can not change password";
	public final static String PASSWORD_INCORRECT = "Password is incorrect.";
	public final static String PASSWORD_NOT_MATCH = "The passwords do not match. Try again!";
	public final static String PASSWORD_INCORRECT_LENGTH = "Password must be more than five characters. Try again!";

	public static final String NEED_LOGIN = "Please authenticate";
	public static final String INVALID_USER = "Invalid email or password.";

	public static final String LOG_OUT_SUCCESS = "Logging out is success.";

	public static final String CAN_NOT_FIND_EXAM = "Can not find exam.";
	public static final String CAN_NOT_FIND_QUESTION = "Can not find question.";


	public static final long ONE_DAY = 24 * 60 * 60 * 1000;
	public static final long ROUND_NUMBER = 10000;

	public static final String ROOT_URL = "http://10.190.201.76";
	public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

	/**
	 * Constant of role
	 */
	public static final int ACCOUNT_ROLE_USER = 1;
	public static final int ACCOUNT_ROLE_ADMIN = 2;


    // some pattern for matching

    public static final char[]pattern1 = {'A'};
    public static final char[]pattern2 = {'A','B'};
    public static final char[]pattern3 = {'A','B','C'};
    public static final char[]pattern4 = {'A','B','C','D'};
    public static final char[]pattern5 = {'A','B','C','D','E'};
    public static final char[]pattern6 = {'A','B','C','D','E','F'};
    public static final char[]pattern7 = {'A','B','C','D','E','F','G'};
    public static final char[]pattern8 = {'A','B','C','D','E','F','G','H'};
    public static final char[]pattern9 = {'A','B','C','D','E','F','G','H','I'};
    public static final char[]pattern10 = {'A','B','C','D','E','F','G','H','I','J'};
}
