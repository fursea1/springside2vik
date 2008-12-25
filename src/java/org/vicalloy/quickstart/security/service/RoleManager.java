package org.vicalloy.quickstart.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springside.core.dao.extend.HibernateEntityExtendDao;
import org.vicalloy.quickstart.security.model.MasterFunction;
import org.vicalloy.quickstart.security.model.Role;
import org.vicalloy.quickstart.security.model.SubFunction;

public class RoleManager extends HibernateEntityExtendDao<Role> {

	@SuppressWarnings("unchecked")
	public List<MasterFunction> getMasterFunctions() {
		return this.find("from MasterFunction order by functionKey");
	}

	@SuppressWarnings("unchecked")
	public List<SubFunction> getSubFunctions(MasterFunction masterFunction) {
		return this.find(
				"from SubFunction where masterFunction=? order by functionKey",
				masterFunction);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map> getFunctions() {
		List<Map> functions = new ArrayList<Map>();
		List<MasterFunction> masterFunctions = getMasterFunctions();
		for (MasterFunction mf : masterFunctions) {
			Map m = new HashMap();
			m.put("master", mf);
			List<SubFunction> subFunctions = getSubFunctions(mf);
			m.put("subs", subFunctions);
			functions.add(m);
		}
		return functions;
	}
}
