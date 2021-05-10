package main.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import datenschicht.EntityManagerUtil;

public class EntityManagerModul extends AbstractModule {

	protected void configure(){
		bind(EntityManagerUtil.class).in(Singleton.class);
	}
	
}
