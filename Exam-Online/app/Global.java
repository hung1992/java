import java.util.List;
import java.util.Map;

import models.Account;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Yaml;

import com.avaje.ebean.Ebean;

public class Global extends GlobalSettings {
	public void onStart(Application app) {
		Logger.info("Application has started");
		
		// insert default data.
		InitialData.insert(app);
	}

	public void onStop(Application app) {
		Logger.info("Application shutdown...");
	}

	static class InitialData {

		public static void insert(Application app) {
			if (Ebean.find(Account.class).findRowCount() == 0) {

						        
				@SuppressWarnings("unchecked")
				Map<String, List<Object>> all = (Map<String, List<Object>>) Yaml.load("initial-data.yml");
				
				System.out.println(all.values());
				// Insert users first
				Ebean.save(all.get("accounts"));

				Ebean.save(all.get("questions"));
				Ebean.save(all.get("quizzes"));
				Ebean.save(all.get("exams"));
				Ebean.save(all.get("exam_question"));
////
//				Ebean.save(all.get("quizzes"));
////
//				Ebean.save(all.get("exams"));
//
//				// Insert stories first
//				Ebean.save(all.get("stories"));
			}
		}

	}
}