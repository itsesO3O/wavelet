import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    protected int DEFAULT_LENGTH = 100;
    String[] words = new String[DEFAULT_LENGTH];
    int currIndex = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Welcome to the Search Engine.");
        } else if(url.getPath().equals("/add")){
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                words[currIndex] = parameters[1];
                currIndex++;
                return (parameters[1] + " has been added to the list!");
            }
        } else if(url.getPath().equals("/search")){
            String[] parameters = url.getQuery().split("=");
            if(parameters[0].equals("s")){
                String matches = "";
                if(currIndex != 0){
                    for(int i = 0; i < currIndex; i++){
                        if(words[i].indexOf(parameters[1]) >= 0){
                          matches += words[i] + "\n";
                        }
                    }
                }

                if (matches.equals("")){
                    return "No words found!";
                } else{
                    return "Following words found: \n" + matches;
                }
                
            }
            
        }
        return String.format("404 Nothing Found");
        }
    }


class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
} 
