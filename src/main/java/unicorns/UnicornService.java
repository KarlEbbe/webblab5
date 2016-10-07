package unicorns;

import static spark.Spark.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ClasspathLoader;

import spark.ModelAndView;
import spark.Request;
import spark.template.pebble.PebbleTemplateEngine;

/**
 * Enhörningsdatabasen
 * 
 * @author Johan Holmberg
 */
public class UnicornService {

	public static void main(String[] args) throws Exception {
		Gson gson = new Gson();
		Storage storage = new Storage();
		storage.setup();
		ClasspathLoader loader = new ClasspathLoader();
		loader.setPrefix("templates");
		
		port(8080);
		
		// Hämtar en lista över alla enhörningar
		get("/", (request, response) -> {
			if("application/json".equals(preferredResponseType(request)) ){
				System.out.println(preferredResponseType(request));
				response.body(gson.toJson(storage.fetchUnicorns()));
				response.type("application/json");
				response.header("Access-Control-Allow-Origin", "*");
				response.status(200);				
			}else if("text/html".equals(preferredResponseType(request))){
					List<Unicorn> unicorns = storage.fetchUnicorns();
					Map<String, Object> model = new HashMap<>();
					model.put("unicorns", unicorns); 
					PebbleTemplateEngine engine = new PebbleTemplateEngine(loader);
					response.body(engine.render(new ModelAndView(model, "list.tpl")));
					response.header("Access-Control-Allow-Origin", "*");

					response.status(200);	
			}else{
				response.body(gson.toJson(storage.fetchUnicorns()));
				response.header("Access-Control-Allow-Origin", "*");
				response.status(200);
			}
			return response.body(); // Skicka tillbaka svaret
		});

		get("/:id", (request, response) -> {
			int id = Integer.parseInt(request.params("id"));
			if("application/json".equals(preferredResponseType(request))){
				response.body(gson.toJson(storage.fetchUnicorn(id)));
				response.type("application/json;charset=utf-8");
				response.header("Access-Control-Allow-Origin", "*");
				response.status(200);			
			}else if("text/html".equals(preferredResponseType(request))){
				String s = gson.toJson(storage.fetchUnicorn(id));
				Map<String, Object> model = new Gson().fromJson(s, Map.class);
				PebbleTemplateEngine engine = new PebbleTemplateEngine(loader);
				response.body(engine.render(new ModelAndView(model, "details.tpl")));
				response.header("Access-Control-Allow-Origin", "*");
				response.status(200);
			}
			return response.body(); // Skicka tillbaka svaret
		});

		post("/", (request, response) -> {
			response.body(""); // Sätt ett tomt svar
			String json = request.body();
			storage.addUnicorn(gson.fromJson(json,Unicorn.class));
			response.header("Access-Control-Allow-Origin", "*");
			response.status(200);
			
			return response.body(); // Skicka tillbaka svaret
		});

		put("/:id", (request, response) -> {
			response.body(""); // Sätt ett tomt svar
			int idToUpdate = Integer.parseInt(request.params("id"));
			Unicorn unicornToUpdate = storage.fetchUnicorn(idToUpdate);
			Unicorn unicornContainingNewData = (Unicorn)gson.fromJson(request.body(),Unicorn.class);
			unicornToUpdate.id = unicornContainingNewData.id;
			unicornToUpdate.name = unicornContainingNewData.name;
			unicornToUpdate.description = unicornContainingNewData.description;
			unicornToUpdate.reportedBy = unicornContainingNewData.reportedBy;
			unicornToUpdate.spottedWhere = unicornContainingNewData.spottedWhere;
			unicornToUpdate.image = unicornContainingNewData.image;
			storage.updateUnicorn(unicornToUpdate);
			response.header("Access-Control-Allow-Origin", "*");
			response.status(200);
			
			return response.body(); // Skicka tillbaka svaret
		});

		delete("/:id", (request, response) -> {
			response.body(""); // Sätt ett tomt svar
			int id = Integer.parseInt(request.params("id"));
			storage.deleteUnicorn(id);
			response.header("Access-Control-Allow-Origin", "*");
			response.status(200);
			
			return response.body(); // Skicka tillbaka svaret
		});		
	}

	private static String preferredResponseType(Request request) {
		// Ibland skickar en klient en lista av format som den önskar.
		// Här splittar vi upp listan och tar bort eventuella mellanslag.
		List<String> types = Arrays.asList(request.headers("Accept").split("\\s*,\\s*"));
		
		// Gå igenom listan av format och skicka tillbaka det första som vi stöder
		for (String type: types) {
			switch (type) {
			case "application/json":
			case "application/xml":
			case "text/html":
				return type;
			default:
			}
		}
		
		// Om vi inte stöder något av formaten, skicka tillbaka det första formatet
		return types.get(0);
	}
	
	/**
	 * Spara detta som exempelkod!!
	 		Set set = request.headers();
			String s = gson.toJson(storage.fetchUnicorn(1));
			String s = gson.toJson(storage.fetchUnicorns());
			
			Map<String, Object> model = new Gson().fromJson(s, Map.class);
			PebbleTemplateEngine engine = new PebbleTemplateEngine(loader);
			String ret = engine.render(new ModelAndView(model, "details.tpl"));
			String ret = engine.render(new ModelAndView(model, "list.tpl"));
			System.out.println(engine.render(new ModelAndView(model, "details.tpl")));
			System.out.println(engine.render(new ModelAndView(model, "list.tpl")));
	 */

}
