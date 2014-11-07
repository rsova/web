package utils

class StringUtil {
	//Little helper method to make text more human readable
	static String splitCamelCase(String s) {
		return s.replaceAll(
		   String.format("%s|%s|%s",
			  "(?<=[A-Z])(?=[A-Z][a-z])",
			  "(?<=[^A-Z])(?=[A-Z])",
			  "(?<=[A-Za-z])(?=[^A-Za-z])"
		   ),
		   " "
		);
	 }
}
