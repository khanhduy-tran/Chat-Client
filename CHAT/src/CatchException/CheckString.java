package CatchException;


public class CheckString {
	public static void CheckUserName(String username) throws Exception {
		if(username.length()==0) throw new Exception("Vui lòng nhập tên người dùng!");
		else if(username.length()<8) throw new Exception("Tên người dùng không được quá ngắn!");
		else if(username.length()>20) throw new Exception("Tên người dùng không được quá dài!");
		else if(!username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$")) throw new Exception("Tên người dùng không được chứa kí tự đặc biệt!");
		else return;
	}
	public static void CheckLoginPassword(String password) throws Exception {
		if(password.length()==0) throw new Exception("Vui lòng nhập mật khẩu!");
	}
	public static void CheckLoginUser(String user) throws Exception {
		if(user.length()==0) throw new Exception("Vui lòng nhập tài khoản!");
	}
	public static void CheckRegisterPassword(String password, String password2) throws Exception {
		if(password.length()==0) throw new Exception("Vui lòng nhập mật khẩu!");
		else
		if(password.length()<8) throw new Exception("Mật khẩu quá ngắn!");
		else
		if(password2.length()==0) throw new Exception("Vui lòng xác nhận lại mật khẩu!");
		else
		if(!password.equals(password2)) throw new Exception("Mật khẩu và mật khẩu xác nhận không giống nhau!");
	}
	public static void CheckEmail(String email) throws Exception {
		if(email.length()==0) throw new Exception("Vui lòng nhập email!");
		else
		if(!email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
			throw new Exception("Địa chỉ Email không hợp lệ!");
		else return;
	}
	
}
