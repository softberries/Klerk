package com.softberries.klerk.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;
import com.softberries.klerk.dao.*;
import com.softberries.klerk.dao.entity.Product;

public class ExitHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		Map<String, Object> props = new HashMap<String,Object>();
		props.put("eclipselink.ddl-generation", "create-tables");
		props.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		props.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/klerk");
		props.put("javax.persistence.jdbc.user", "root");
		props.put("javax.persistence.jdbc.password", "adminadmin");
		ProductDAO dao = new ProductDAO();
		
		dao.createProduct("ABC","testowy produkt");
		HandlerUtil.getActiveWorkbenchWindow(event).close();
		return null;
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public boolean isEnabled() {
		//for now make it always enabled (and maybe forever)
		return true;
	}

	@Override
	public boolean isHandled() {
		//for now make it always return true
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		
	}

}
