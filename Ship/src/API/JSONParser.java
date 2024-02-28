package API;
import org.json.JSONObject;

public class JSONParser {

	public JSONObject parseToJSON(String...keyValuePairs) {
		
		if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Ungültige String-Anzahl. Es müssen Key-Value-Paare sein.");
        }
		
		JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = keyValuePairs[i];
            String value = keyValuePairs[i + 1];
            jsonObject.put(key, value);
        }

		return jsonObject;
		
	}
	
}
