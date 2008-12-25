package org.hibernate.cfg;

/*******************************************************************************
 * hibernate的名字策略，为表名提供前缀
 * 
 * @author vicalloy
 * 
 */
public class PrefixNamingStrategy extends ImprovedNamingStrategy {

	private static final long serialVersionUID = 7576677914448134356L;

	private String prefix = "";

	public static final NamingStrategy INSTANCE = new PrefixNamingStrategy();

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override
	public String tableName(String tableName) {
		if (super.tableName(tableName).startsWith(prefix)) {
			return super.tableName(tableName);
		}
		return prefix + super.tableName(tableName);
	}

	@Override
	public String classToTableName(String className) {
		if (super.classToTableName(className).startsWith(prefix)) {
			return super.classToTableName(className);
		}
		return prefix + super.classToTableName(className);
	}
}
