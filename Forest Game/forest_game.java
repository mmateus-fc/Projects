/* ***************************************
@author    Mateus Franco e Cunha
@SID       220530325
@date      03 December 2023
@version   3

	Mini Project - Explore the forest game 

	A board game that allows the player to wander through a forest.
	Through the board game, the player can:
		heal sick animals, 
		collect plants,
		fall into traps,
		discover new environments. 

   ****************************************/

   import java.io.*;
   import java.util.Scanner;
   import java.util.Random;
   
   class Family
   {
       String specie;
       String name;
       String description;
       int points;
   }
   
   class Player
   {
       String name;
       int points; // number of healing points
       int board; // number of the spaces walked
       int rounds; // number of rounds played
       int animals; // number of animals found
       int plants; // number of plants found
       int traps; // number of traps found
       int environments; // number of environments found
       String [] animals_list; // animals founds
       String [] plants_list; // plants founds
       String [] traps_list; // traps founds
       String [] environments_list; // environments founds
   }
   
   // Class for Forest Game
   public class forest_game {
       
       public static void main (String[] args) throws IOException
       { 
            // Welcome message
           welcome();
           
           // Game main variables
           final int board = 100; // board size
           final int rounds = board; // rounds are at minimum, the size of the board
           final int points = 50; // player starting points
   
            // Rules
           rules(board, rounds);
           
           // Creating animals
           final Family[] animals = animals();
   
           // Creating plants
           final Family[] plants = plants();
   
           // Creating traps
           final Family[] traps = traps();
   
           // Creating environments
           final Family[] environments = environments();
   
           // Output file with details
           file_details_elements(animals, plants, traps, environments);
           
           // Start new game or load game
           boolean new_game = new_or_load();
           print_new_load(new_game);
           
           if (new_game) // new game 
           {
               // Adventurer`s name
               String adventurer_name = adventurer_name();
   
               // Create player
               Player player = new Player();
               player = create_player(adventurer_name, points, rounds);
               
               // play game
               play(player, board, rounds, animals, plants, traps, environments);            
   
               // save game
               save_game(player, board, rounds);
               
               // print final results
               print_results(player);
               print_results_file(player);
           }
               
           else // load game
           {
               // Create player
               Player player = new Player();
               player = create_player("None", points, rounds);
   
               // load game
               load_game(player);
               
               // play game
               play(player, board, rounds, animals, plants, traps, environments);   
               
               // save game
               save_game(player, board, rounds);   
   
               // print final results
               print_results(player);
               print_results_file(player);
           }
   
           return;        
       } // END of MAIN
       
       // Welcome message
       public static void welcome ()
       {
           String message_welcome;
           String message_explain;
           
           message_welcome = "--*--*--*--*-- Welcome to the Forest Game! --*--*--*--*--\n";
           message_explain = "In this game you will wander through a forest, healing sick animals and collecting plants that improve your healing skills.";
   
           System.out.println(message_welcome);
           System.out.println(message_explain);
   
           line_break(); // line break to show the end of a task or method
           
           return;
       } // END of welcome
       
       // Method to print lines to indicate the end of a method, functin or game step
       public static void line_break ()
       {
           System.out.println();
           System.out.println("-----------------------------------------------------------");
           System.out.println();
   
           return;
       } // END of line_break
       
       // Print game rules
       public static void rules(int board, int rounds)
       {
           System.out.println("--*--*--*--*-- Rules and instructions --*--*--*--*-- \n");
   
           String rules =   "1. The board game has " + board + " spaces.\n" 
                          + "2. You have " + rounds + " maximum rounds to reach the end of the forest.\n" 
                          + "3. Each round, you will roll a dice with numbers from 1 to 6.\n" 
                          + "4. The numbers rolled in rule (3) determine the number of spaces you move through the forest.\n" 
                          + "5. Upon reaching a new space, the game will inform you about its type:\n" 
                          + "   an environment, an animal, a plant, or a trap.\n" 
                          + "6. If it is an environment, plant, or trap:\n" 
                          + "   No action is required.\n" 
                          + "   You will gain or lose points accordingly.\n" 
                          + "7. If it is an animal:\n" 
                          + "   Action is required.\n" 
                          + "   You must choose to try to heal the animal or do nothing.\n" 
                          + "8. If you choose to try to heal the animal, roll a dice from 1 to 10.\n" 
                          + "9. If the number rolled in step (8) is greater than the required number to heal the animal, you succeed.\n" 
                          + "   Otherwise, you lose the amount required from your points.\n" 
                          + "10. You can also refer to the Animals, Plants, Traps, and Environments files to confirm the corresponding points you will gain or lose.";      
   
           System.out.println(rules);
           
           line_break();
               
           return;
       } // END of rules
       
       public static Family[] animals()
       {
           String type = "animal";
           
           // creating a arrays that has the description of animals' name, details of the description and points to heal
           String[] name = { // 25 animals
               "Badger", "Beaver", "Bobcat", "Boar", // 1, 2, 3, 4
               "Chipmunk", // 5
               "Deer", "Duck", // 6, 7
               "Eagle", // 8
               "Ferret", "Fox", // 9, 10
               "Hawk", // 11
               "Hummingbird", // 12 
               "Koala", // 13
               "Lizard", "Lynx", // 14, 15
               "Mink", "Moose",  // 16, 17
               "Owl", // 18
               "Raccoon", "Rabbit", // 19, 20
               "Skunk", "Squirrel", // 21, 22
               "Weasel", "Wildcat", "Wolf" // 23, 24, 25
           };
           
           String[] description = {
               "Bruises and minor cuts", // Badger // 1
               "Sprains and surface scratches", // Beaver // 2
               "Claw scrapes and surface bruises", // Bobcat // 3
               "Surface cuts and light bruises", // Boar // 4
               "Sprains and minor cuts", // Chipmunk // 5
               "Surface cuts and light bruises", // Deer // 6
               "Hook or snare scratches", // Duck // 7
               "Surface talon scrapes", // Eagle // 8
               "Nips and surface scratches", // Ferret // 9
               "Various surface scratches", // Fox // 10
               "Light talon scrapes", // Hawk // 11
               "Exhaustion and bruises", // Hummingbird // 12
               "Light burns and scratches", // Koala // 13
               "Tail nicks and bruises", // Lizard // 14
               "Claw scrapes and surface bruises", // Lynx // 15
               "Water-related scratches", // Mink // 16
               "Surface cuts and light bruises", // Moose // 17
               "Minor eye irritations", // Owl // 18
               "Various surface scratches", // Raccoon // 19
               "Surface leg abrasions and sprains", // Rabbit // 20
               "Sprains and surface scratches", // Skunk // 21
               "Minor limb scrapes and bruises", // Squirrel // 22 
               "Minor bite marks and bruises", // Weasel // 23
               "Claw scrapes and surface bruises", // Wildcat // 24
               "Surface bites and scratches" // Wolf // 25
           };
           
           int[] points = {
               4, 7, 6, 9, // "Badger", "Beaver", "Bobcat", "Boar" // 1, 2, 3, 4
               2, // "Chipmunk" // 5
               8, 5, // "Deer", "Duck", // 6, 7
               7, // "Eagle", // 8
               3, 6, // "Ferret", "Fox" // 9, 10
               8, // "Hawk" // 11
               2, // "Hummingbird" // 12
               3, // "Koala" // 13
               2, 5, // "Lizard", "Lynx" // 14, 15
               3, 9, // "Mink", "Moose" // 16, 17
               4, // "Owl" // 18 
               6, 6, // "Raccoon", "Rabbit" // 19, 20
               5, 4, // "Skunk", "Squirrel" // 21, 22
               3, 7, 8 // "Weasel", "Wildcat", "Wolf" // 23, 24, 25
           }; 
   
           // creating an array of records
           Family[] records = create_records(type, name, description, points);
               
           return records;
       } // END of animals	
       
       public static Family[] plants()
       {
           String type = "plant";
           
           // creating a arrays that has the description of plants' name, details of the description and points to heal
           String[] name = { // 39 plants
             "Apple Tree", // 1
             "Baobab Tree", // 2
             "Blackberry Bush", // 3
             "Blueberry Bush", // 4
             "Boxwood Bush", // 5
             "Butterfly Bush", // 6
             "Cactus", // 7
             "Carnivorous Pitcher Plant", // 8
             "Celestial Crystal Ferns", // 9
             "Cherry Blossom Tree", // 10
             "Dragon's Breath Orchid", // 11
             "Ethereal Wisps", // 12
             "Fern", // 13
             "Forsythia Bush", // 14
             "Glowing Starlilies", // 15
             "Holly Bush", // 16
             "Ivy", // 17
             "Juniper Bush", // 18
             "Lavender", // 19
             "Lavender Bush", // 20
             "Maple Tree", // 21
             "Moonshadow Vines", // 22
             "Oak Tree", // 23
             "Orchid", // 24
             "Palm Tree", // 25
             "Phoenix Feather Bush", // 26
             "Pine Tree", // 27
             "Redwood Tree", // 28
             "Rose", // 29
             "Rosemary Bush", // 30
             "Raspberry Bush", // 31
             "Siren's Sigh Blossoms", // 32
             "Stardust Willow", // 33
             "Sunflower", // 34
             "Tulip", // 35
             "Venus Flytrap", // 36
             "Wandering Dreamroot", // 37
             "Whispering Ghostwood", // 38
             "Willow Tree" // 39
           };
           
           String[] description = {
             "Tree bearing crisp and juicy apples.", // 1 - Apple Tree
             "Iconic tree with a massive trunk native to Africa.", // 2 - Baobab Tree
             "Bush producing plump and flavorful blackberries.", // 3 - Blackberry Bush
             "Bush bearing sweet and juicy blueberries.", // 4 - Blueberry Bush
             "Dense and compact bush commonly used for hedging.", // 5 - Boxwood Bush
             "Bush attracting butterflies with its colorful blooms.", // 6 - Butterfly Bush
             "A desert succulent with spines and beautiful flowers.", // 7 - Cactus
             "A unique plant with pitcher-shaped leaves for trapping insects.", // 8 - Carnivorous Pitcher Plant
             "Ferns with crystal-like formations, shining in moonlight.", // 9 - Celestial Crystal Ferns
             "Ornamental tree with delicate pink blossoms.", // 10 - Cherry Blossom Tree
             "Exotic orchid with fiery-colored blooms resembling dragon breath.", // 11 - Dragon's Breath Orchid
             "Delicate wisps of light that hover around a magical plant.", // 12 - Ethereal Wisps
             "A non-flowering plant with delicate, feathery leaves.", // 13 - Fern
             "Bush with bright yellow flowers, signaling the arrival of spring.", // 14 - Forsythia Bush
             "Ethereal flowers that emit a soft, magical glow.", // 15 - Glowing Starlilies
             "Evergreen bush with red berries, often associated with winter.", // 16 - Holly Bush
             "Climbing plant with green, lush leaves, often used for decoration.", // 17 - Ivy
             "Bush with aromatic berries used in flavoring.", // 18 - Juniper Bush
             "Fragrant herb with spikes of small purple flowers.", // 19 - Lavender
             "Bush covered in aromatic lavender flowers.", // 20 - Lavender Bush
             "Tree known for its vibrant autumn foliage.", // 21 - Maple Tree
             "Vines that shimmer in moonlight, creating a mystical atmosphere.", // 22 - Moonshadow Vines
             "Majestic tree with broad, sturdy branches and acorns.", // 23 - Oak Tree
             "An exotic flower known for its intricate and diverse species.", // 24 - Orchid
             "Tropical tree with large, fan-shaped leaves.", // 25 - Palm Tree
             "Bush adorned with fiery-colored leaves resembling phoenix feathers.", // 26 - Phoenix Feather Bush
             "Evergreen tree with needle-like leaves and pine cones.", // 27 - Pine Tree
             "Towering tree with reddish-brown bark.", // 28 - Redwood Tree
             "Classic flower symbolizing love and beauty, available in various colors.", // 29 - Rose
             "A fragrant bush with needle-like leaves used in cooking.", // 30 - Rosemary Bush
             "Bush with clusters of delicious red raspberries.", // 31 - Raspberry Bush
             "Flowers that emit a melodic sigh, enchanting those who hear it.", // 32 - Siren's Sigh Blossoms
             "Willow tree with leaves that glisten like stardust.", // 33 - Stardust Willow
             "A vibrant, sun-loving flower with large yellow petals.", // 34 - Sunflower
             "Colorful, cup-shaped flowers that bloom in spring.", // 35 - Tulip
             "A carnivorous plant with hinged leaves that trap insects.", // 36 - Venus Flytrap
             "Plant with roots that wander, bringing vivid dreams to those nearby.", // 37 - Wandering Dreamroot
             "Mysterious plant with ghostly white bark and leaves.", // 38 - Whispering Ghostwood
             "Tree with graceful, drooping branches and slender leaves." // 39 - Willow Tree
           };
           
           int[] points = {
               1, // 1 - Apple Tree
               2, // 2 - Baobab Tree
               1, // 3 - Blackberry Bush
               2, // 4 - Blueberry Bush
               1, // 5 - Boxwood Bush
               3, // 6 - Butterfly Bush
               1, // 7 - Cactus
               2, // 8 - Carnivorous Pitcher Plant
               4, // 9 - Celestial Crystal Ferns
               3, // 10 - Cherry Blossom Tree
               4, // 11 - Dragon's Breath Orchid
               2, // 12 - Ethereal Wisps
               2, // 13 - Fern
               2, // 14 - Forsythia Bush
               4, // 15 - Glowing Starlilies
               2, // 16 - Holly Bush
               3, // 17 - Ivy
               2, // 18 - Juniper Bush
               3, // 19 - Lavender
               2, // 20 - Lavender Bush
               3, // 21 - Maple Tree
               4, // 22 - Moonshadow Vines
               3, // 23 - Oak Tree
               3, // 24 - Orchid
               3, // 25 - Palm Tree
               4, // 26 - Phoenix Feather Bush
               3, // 27 - Pine Tree
               2, // 28 - Redwood Tree
               4, // 29 - Rose
               2, // 30 - Rosemary Bush
               2, // 31 - Raspberry Bush
               4, // 32 - Siren's Sigh Blossoms
               4, // 33 - Stardust Willow
               4, // 34 - Sunflower
               3, // 35 - Tulip
               3, // 36 - Venus Flytrap
               1, // 37 - Wandering Dreamroot
               2, // 38 - Whispering Ghostwood
               4  // 39 - Willow Tree
           }; 
   
           // creating an array of records
           Family[] records = create_records(type, name, description, points);
               
           return records;
       }  // END of plants
       
       public static Family[] traps()
       {
           String type = "trap";
           
           // creating a arrays that has the description of traps' name, details of the description and points to heal
           String[] name = { // 10 traps
             "Thorn Patch", // 1
             "Quicksand Pit", // 2
             "Enchanted Web", // 3
             "Mystic Mist", // 4
             "Thief's Glade", // 5
             "Sleeping Spores", // 6
             "Entangling Vines", // 7
             "Illusory Mirage", // 8
             "Shadowy Stalkers", // 9
             "Echoing Whisper" // 10
           };
           
           String[] description = {
             "A patch of thorns that inflicts a painful penalty.", // 1 - Thorn Patch
             "A pit of quicksand that hinders movement significantly.", // 2 - Quicksand Pit
             "An enchanted web with magic that imposes a penalty.", // 3 - Enchanted Web
             "Mystical mist that causes disorientation with a penalty.", // 4 - Mystic Mist
             "A glade frequented by thieves, resulting in a penalty.", // 5 - Thief's Glade
             "Spores that induce sleep, causing a penalty.", // 6 - Sleeping Spores
             "Vines that entangle and impede progress with a penalty.", // 7 - Entangling Vines
             "An illusionary mirage that confuses with a penalty.", // 8 - Illusory Mirage
             "Shadowy stalkers that inflict a penalty.", // 9 - Shadowy Stalkers
             "Whispers that echo, leading to a penalty." // 10 - Echoing Whisper
           };
           
           int[] points = {
             -3, // 1 - Thorn Patch
             -4, // 2 - Quicksand Pit
             -4, // 3 - Enchanted Web
             -3, // 4 - Mystic Mist
             -2, // 5 - Thief's Glade
             -3, // 6 - Sleeping Spores
             -1, // 7 - Entangling Vines
             -2, // 8 - Illusory Mirage
             -1, // 9 - Shadowy Stalkers
             -2 // 10 - Echoing Whisper
           }; 
   
           // creating an array of records
           Family[] records = create_records(type, name, description, points);
               
           return records;
       } // END of traps
   
       public static Family[] environments()
       {
           String type = "environment";
           
           // creating a arrays that has the description of environment' name, details of the description and points to heal
           String[] name = { // 18 environments
             "Aurora Glade", // 1
             "Blossoming Cherry Trees", // 2
             "Crystal Clear Stream", // 3
             "Cursed Swamp", // 4
             "Desert Oasis", // 5
             "Enchanting Meadow", // 6
             "Eternal Snow Peaks", // 7
             "Foggy Valley", // 8
             "Golden Sunset Beach", // 9
             "Majestic Mountain View", // 10
             "Mild Breeze Garden", // 11
             "Moonlit Forest", // 12
             "Serene Waterfall", // 13
             "Stormy Skies", // 14
             "Sunrise Over Hills", // 15
             "Thunderstorm Retreat", // 16
             "Tranquil Lakeside", // 17
             "Whispering Willow Grove" // 18
           };
           
           String[] description = {
             "Be enchanted by the vibrant colors of an aurora glade.", // 1 - Aurora Glade
             "Enjoy the blossoming cherry trees in full bloom.", // 2 - Blossoming Cherry Trees
             "Refresh yourself by a crystal-clear stream.", // 3 - Crystal Clear Stream
             "The cursed swamp brings discomfort and a penalty.", // 4 - Cursed Swamp
             "Find relief in a desert oasis with unique vegetation.", // 5 - Desert Oasis
             "You discover an enchanting meadow that fills you with wonder.", // 6 - Enchanting Meadow
             "Endure the challenges of eternal snow peaks.", // 7 - Eternal Snow Peaks
             "Navigate through a foggy valley.", // 8 - Foggy Valley
             "Witness a golden sunset on the beach.", // 9 - Golden Sunset Beach
             "A majestic mountain view takes your breath away.", // 10 - Majestic Mountain View
             "Experience a gentle breeze in the mild breeze garden.", // 11 - Mild Breeze Garden
             "The moonlit forest creates a mystical atmosphere with a penalty.", // 12 - Moonlit Forest
             "Marvel at the serenity of a serene waterfall.", // 13 - Serene Waterfall
             "Face the challenges of stormy skies.", // 14 - Stormy Skies
             "Witness the beauty of the sunrise over hills.", // 15 - Sunrise Over Hills
             "Seek shelter from a thunderstorm and take a break.", // 16 - Thunderstorm Retreat
             "Rest by the tranquil lakeside and enjoy the serenity.", // 17 - Tranquil Lakeside
             "Listen to the whispers of the willow grove." // 18 - Whispering Willow Grove
           };
           
           int[] points = {
             3, // 1 - Aurora Glade
             2, // 2 - Blossoming Cherry Trees
             2, // 3 - Crystal Clear Stream
             -3, // 4 - Cursed Swamp
             3, // 5 - Desert Oasis
             3, // 6 - Enchanting Meadow
             -2, // 7 - Eternal Snow Peaks
             -2, // 8 - Foggy Valley
             2, // 9 - Golden Sunset Beach
             3, // 10 - Majestic Mountain View
             1, // 11 - Mild Breeze Garden
             -2, // 12 - Moonlit Forest
             2, // 13 - Serene Waterfall
             -2, // 14 - Stormy Skies
             3, // 15 - Sunrise Over Hills
             -3, // 16 - Thunderstorm Retreat
             2, // 17 - Tranquil Lakeside
             2 // 18 - Whispering Willow Grove
           }; 
   
           // creating an array of records
           Family[] records = create_records(type, name, description, points);
               
           return records;
       } // END of environments
       
       // Function to create and array or record
       public static Family[] create_records(String type, String name[], String[] description, int[] points)
       {
           // array of records
           Family[] records = new Family[name.length];
           
           // iterating over the array records
           for (int i = 0; i < records.length; i++)
           {
               // creating the record
               Family specie_record = create_specie(type, name[i], description[i], points[i]);
   
               // input the record on the array
               records[i] = specie_record;      
           }
   
           return records;
       } // END of create_records
   
       // ADT - Accessor method (function) that creates the record
       public static Family create_specie(String given_type, String given_name, String given_description, int given_points)
       {
           Family record = new Family();
   
           record.specie = given_type;
           record.name = given_name;
           record.description = given_description;
           record.points = given_points;
   
           return record;
       } // END of create_specie
   
       // ADT - Accessor method (function) that returns the specie 
       public static String get_family_specie(Family record)
       {
           return record.specie;
       } // END of get_family_specie
   
       // ADT - Accessor method (function) that returns the name
       public static String get_family_name(Family record)
       {
           return record.name;
       } // END of get_family_name
   
       // ADT - Accessor method (function) that returns the description
       public static String get_family_description(Family record)
       {
           return record.description;
       } // END of get_family_description
   
       // ADT - Accessor method (function) that returns the points
       public static int get_family_points(Family record)
       {
           return record.points;
       } // END of get_family_points
   
       // Method (procedure) that iterate for each array and send each one of them to another method and informe the user which file are being created.
       public static void file_details_elements(Family[] animals, Family[] plants, Family[] traps, Family[] environments) throws IOException
       {
           // Print head message
           String head_text = "Creating detailed files for ";
   
           String[] types_names = {"Animals", "Plants", "Traps", "Environments"};
           
           for (int i = 0; i < types_names.length; i++)
           {
               if (i == (types_names.length - 1))
               {
                   head_text = head_text + types_names[i];
               }
   
               else
               {
                   head_text = head_text + types_names[i] + ", ";
               }
           }
           System.out.println(head_text);
           
           Family[][] types_records = {animals, plants, traps, environments};
           
           for (int i = 0; i < types_names.length; i++)
           {
               String type_name_i = types_names[i];
               Family[] records_i = types_records[i];
                     
               create_file_elements("txt", type_name_i, records_i);
           }
   
           System.out.println("Detailed files created.");
           line_break();
           
           return;
       } // END of file_details_elements
   
       // Method (procedure) that creates a file and send an array with record data to a procedure to print data
       public static void create_file_elements(String file_extension, String given_name, Family[] records) throws IOException
       {
           String file_name = given_name + "." + file_extension; // csv, txt
   
           PrintWriter file = new PrintWriter (new FileWriter(file_name));
   
           // print accordingly
           print_detailed_table(file, records, given_name);
   
           System.out.println(file_name + " was written.");
           
           file.close();
   
           line_break();
   
           return;
       } // END of create_file_elements
   
       // Method to print records as a table
       public static void print_detailed_table (PrintWriter file, Family[] records, String type) throws IOException
       {
           // calculates the length of each column
           int column_name_length = column_length(records, "name") + 5; // adding 5 spaces for readability
           int column_description_length = column_length(records, "description") + 5; // adding 5 spaces for readability
           int column_points_length = 10; // value set because it is only integer
   
           // calculates the size of table line
           String line_name = line_string(column_name_length);
           String line_description = line_string(column_description_length);
           String line_points = line_string(column_points_length);
               
           // Print headline
           file.printf("%-" + column_name_length + "s | %-" + column_description_length + "s | %-" + column_points_length + "s\n", type, "Description", "Points");
   
           // print line headline
           file.printf("%-" + column_name_length + "s | %-" + column_description_length + "s | %-" + column_points_length + "s\n", line_name, line_description, line_points);
           
           // print table
           for (int i = 0; i < records.length; i++) {
               String name = get_family_name(records[i]);
               String description = get_family_description(records[i]);
               int points = get_family_points(records[i]);
               
               file.printf("%-" + column_name_length + "s | %-" + column_description_length + "s | %-" + column_points_length + "d\n", name, description, points);
           }
   
           // print line headline
           file.printf("%-" + column_name_length + "s | %-" + column_description_length + "s | %-" + column_points_length + "s\n", line_name, line_description, line_points);
           
           return;
       } // END of print_detailed_table
   
       // Method (procedure) to calculate the length of strings and return the longest.
       public static int column_length (Family[] records, String column)
       {
           // initial length
           String word = get_family_specie(records[0]);
           int max_length = word.length();
   
           // iterate over records to get max length
           for (int i = 0; i < records.length; i++)
           {
               word = get_family_specie(records[i]);
               int record_length = word.length();
   
               if (column.equals("name"))
               {
                   record_length = get_family_name(records[i]).length();
               }
                   
               else
               {
                   record_length = get_family_description(records[i]).length();
               }
   
               //
               if (record_length > max_length)
               {
                   max_length = record_length;
               }
           }
   
           return max_length;
       } // END of column_length
   
       // Method (function) creates a predefined string pattern with the given size and return it.
       public static String line_string(int column_length)
       {
           String line = "";
   
           for (int i = 1; i <= column_length; i++)
           {
               line = line + "-";
           }
   
           return line;
       } // END of line_string
   
       // Give the user the options to start a new game or load a previous game.
       public static boolean new_or_load()
       {
           // message to user choose the initialization of the game 
           String message_main = ("Would you like to start a new game or load from a previous game?\n");   
   
           String message_options = ("Type accordingly:\n"
                                + "NEW GAME, type [NEW, New, new, 1]\n"
                                + "LOAD GAME, type [LOAD, Load, load, 0]");  
           
           String[] options = {"NEW", "New", "new", "1", "LOAD", "Load", "load", "0"};    
   
           boolean new_game = choose_options(message_main, message_options, options);
   
           line_break();
           
           return new_game;
       } // END of new_or_load
   
       // Prints a message accordingly to what the user chosen: a new game or load a previous one.
       public static void print_new_load (boolean new_game)
       {
           if (new_game == true)
           {
               System.out.println("Okay! Let's start a new game!");
           }
           else
           {
               System.out.println("Okay! Let's load the previous game!");
           }
   
           line_break();
   
           return;
       } // END of print_new_load
   
       // Given a pattern of possible yes or no answer, send them to a function in which the user choose it.
       public static boolean yes_no(String message_main)
       {
           String message_options = ("Type accordingly:\n"
                                + "YES, type [YES, Yes, yes, Y, y, 1]\n"
                                + "NO, type [NO, No, no, N, n, 0]");  
   
           String[] options = {"YES", "Yes", "yes", "Y", "y", "1", "NO", "No", "no", "N", "n", "0"};
   
           boolean yes = choose_options(message_main, message_options, options);
   
           line_break();
   
           return yes;
       } // END of yes_no
   
       // Asks the user to choose between options.
       public static boolean choose_options(String message_main, String message_options, String[] options)
       {
           System.out.println(message_main);
           System.out.println(message_options);
           
           String user_option = user_input(message_options, options);
           
           boolean option_1 = categorize_answer(options, user_option);  
               
           return option_1;
       } // END of choose_options
   
       // Asks the user an input and analyzes with the input is valid (keeps looping until a valid input is given).
       public static String user_input(String message_options, String[] options)
       {
           // user input
           String keyboard_user = read_keyboard();
           
           // valid input
           boolean valid_input = false;
   
           while (valid_input == false)
           {
               // check input valid
               for (int i = 0; i < options.length; i++)
               {
                   String valid_option = options[i];
                   
                   if (keyboard_user.equals(valid_option))
                   {
                       valid_input = true;
                       break;
                   }
               }
               
               if (valid_input == false)
               {
                   // message to user enter a valid input
                   System.out.println("\n___Invalid input___\n"
                                      + "Please enter a valid input.");
                   System.out.println(message_options);
   
                   // new input
                   keyboard_user = read_keyboard();
               }
           }
   
           return keyboard_user;
       } // END of user_input
   
       // Categorize the user`s input as true or false
       public static boolean categorize_answer(String[] options, String user_option)
       {
           boolean option_1 = false;
           
           for (int i = 0; i < (options.length) / 2 ; i++)
           {
               String option = options[i];
   
               if (option.equals(user_option))
               {
                   option_1 = true;
                   break;
               }
           }
   
           return option_1;
       } // END of categorize_answer
   
       // Reads the user keyboard.
       public static String read_keyboard()
       {
           // create the keyboard scanner object
           Scanner scanner_obj = new Scanner(System.in);
   
           // create variable that gets the value of the object keyboard 
           String keyboard_line = scanner_obj.nextLine();
   
           return keyboard_line;
       } // END of read_keyboard
   
       // Method to get from user the adventurer's name
       public static String adventurer_name()
       {
           String message1;
           message1 = ("To start your adventure, let's choose a name for your adventurer!");
           System.out.println(message1);
           
           String message2;
           message2 = "Please, enter your adventurer's name: ";
           System.out.println(message2);
           
           Scanner scanner_obj = new Scanner(System.in);
   
           String adventurer_name;
           adventurer_name = scanner_obj.nextLine();
   
           String message3;
           message3 = ("\nCool name, " + adventurer_name + "!\n" 
                       + "You will have a lot of fun in your adventure in the forest!");
           
           System.out.println(message3);
           
           line_break();
           
           return adventurer_name; 
       } // END of adventurer_name
   
       // ADT - Method to create record of class Player
       public static Player create_player(String given_name, int given_points, int max_rounds)
       {
           Player player = new Player();
           
           player.name = given_name;
           player.points = given_points; // number of healing points
           player.board = 0; // number of the spaces walked
           player.rounds = 0; // number of rounds played
           player.animals = 0; // number of animals found
           player.plants = 0; // number of plants found
           player.traps = 0; // number of traps found
           player.environments = 0; // number of environments found
   
           player.animals_list = new String[max_rounds]; // animals founds, size are the maximum elements existhing in the original array
           player.plants_list = new String[max_rounds]; // plants founds, size are the maximum elements existhing in the original array
           player.traps_list = new String[max_rounds]; // traps founds, size are the maximum elements existhing in the original array
           player.environments_list = new String[max_rounds]; // environments founds, size are the maximum elements existhing in the original array
   
           return player;
       } // END of create_player
   
       // ADT - Method to set value of variable name
       public static void set_player_name (Player player, String given_name)
       {
           player.name = given_name;
               
           return;
       } // END of set_player_name
   
       // ADT - Method to set value of variable points
       public static void set_player_points (Player player, int given_points)
       {
           player.points = given_points;
   
           return;
       } // END of set_player_points
   
       // ADT - Method to set value of variable board
       public static void set_player_board (Player player, int given_board)
       {
           player.board = given_board;
   
           return;
       } // END of set_player_board
   
       // ADT - Method to set value of variable rounds
       public static void set_player_rounds (Player player, int given_rounds)
       {
           player.rounds = given_rounds;
   
           return;
       } // END of set_player_rounds
   
       // ADT - Method to set value of variable animals
       public static void set_player_animals (Player player, int given_animals)
       {
           player.animals = given_animals;
   
           return;
       } // END of set_player_animals
   
       // ADT - Method to set value of variable plants
       public static void set_player_plants (Player player, int given_plants)
       {
           player.plants = given_plants;
   
           return;
       } // END of set_player_plants
   
       // ADT - Method to set value of variable traps 
       public static void set_player_traps (Player player, int given_traps)
       {
           player.traps = given_traps;
   
           return;
       } // END of set_player_traps
   
       // ADT - Method to set value of variable environments
       public static void set_player_environments (Player player, int given_environments)
       {
           player.environments = given_environments;
   
           return;
       } // END of set_player_environments
   
       // ADT - Method to set value of variable animals_list
       public static void set_player_animals_list (Player player, String[] given_animal_list)
       {
           player.animals_list = given_animal_list;
   
           return;
       } // END of set_animals_list
   
       // ADT - Method to set value of variable plants_list 
       public static void set_player_plants_list (Player player, String[] given_plants_list)
       {
           player.plants_list = given_plants_list;
   
           return;
       } // END of set_plants_list
   
       // ADT - Method to set value of variable traps_list
       public static void set_player_traps_list (Player player, String[] given_traps_list)
       {
           player.traps_list = given_traps_list;
   
           return;
       } // END of set_traps_list
   
       // ADT - Method to set value of variable environments_list
       public static void set_player_environments_list (Player player, String[] given_environments_list)
       {
           player.environments_list = given_environments_list;
   
           return;
       } // END of set_environments_list
   
       // ADT - Method to get value of variable name
       public static String get_player_name (Player player)
       {
           return player.name;
       } // END of get_player_name
   
       // ADT - Method to get value of variable points
       public static int get_player_points (Player player)
       {
           return player.points;
       } // END of get_player_points
   
       // ADT - Method to get value of variable board
       public static int get_player_board (Player player)
       {
           return player.board;
       } // END of get_player_board
   
       // ADT - Method to get value of variable rounds
       public static int get_player_rounds (Player player)
       {
           return player.rounds;
       } // END of get_player_rounds
   
       // ADT - Method to get value of variable animals
       public static int get_player_animals (Player player)
       {
           return player.animals;
       } // END of get_player_animals
   
       // ADT - Method to get value of variable plants
       public static int get_player_plants (Player player)
       {
           return player.plants;
       } // END of get_player_plants
   
       // ADT - Method to get value of variable traps
       public static int get_player_traps (Player player)
       {
           return player.traps;
       } // END of get_player_traps
   
       // ADT - Method to get value of variable environments
       public static int get_player_environments (Player player)
       {
           return player.environments;
       } // END of get_player_environments
   
       // ADT - Method to get value of variable animals_list
       public static String[] get_player_animals_list (Player player)
       {
           return player.animals_list;
       } // END of get_player_animals_list
   
       // ADT - Method to get value of variable plants_list
       public static String[] get_player_plants_list (Player player)
       {
           return player.plants_list;
       } // END of get_player_plants_list
   
       // ADT - Method to get value of variable traps_list
       public static String[] get_player_traps_list (Player player)
       {
           return player.traps_list;
       } // END of get_player_traps_list
   
       // ADT - Method to get value of variable environments_list
       public static String[] get_player_environments_list (Player player)
       {
           return player.environments_list;
       } // END of get_player_environments_list
   
       // Method (procedure) that runs the game board in loop while the conditions are valid.
       public static void play (Player player, 
                                int board, 
                                int rounds, 
                                Family[] animals, 
                                Family[] plants, 
                                Family[] traps, 
                                Family[] environments
                               ) 
       {
           System.out.println("The game has started!\n");
           
           /*
           Conditions to keep playing
               1 - Adventurer did not reach the end of the forest
               2 - Adventurer did not use all the rounds available
               3 - Adventurer still have healing points to cure animals
               4 - User wants to keep playing
           */
   
           boolean stop_game = false;
           
           boolean keep_game = keep_game(player, board, rounds, stop_game); // conditions to keep playing
           
           while (keep_game) // conditions to keep playing are valid
           {
               // update rounds
               update_round(player);
               print_round(rounds, get_player_rounds(player));
                   
               // walking on the board
               int to_walk = walking();
   
               // update board
               int previous_board = get_player_board(player); // get value of variable rounds
               set_player_board(player, update_board(get_player_board(player), to_walk)); // 1.get variable of variable round, 
                                                                                          // 2.send values of variables round & to_walk to function update_board
                                                                                          // 3.the function update_board set the value of variable board of the player
               print_board(board, previous_board, get_player_board(player));
   
               // update bool
               keep_game = keep_game(player, board, rounds, stop_game); // keep game 1. updates due to board and rounds
               
               if (keep_game == false) // stop game
               {
                   break;
               }
   
               else // continue game
               {
                   /* 
                   Board possibilities
                       Animals     - 1, 2
                       Plants      - 3, 4
                       Enviroments - 5
                       Traps       - 6
                   */
                   int board_generator = board_generator();
   
                   if (board_generator == 1 || board_generator == 2) // space animal
                   {
                       space_action("animal", player, animals);
                   }
                       
                   else if (board_generator == 3 || board_generator == 4) // space plant
                   {
                       space_action("plant", player, plants);
                   }
                       
                   else if (board_generator == 5) // space environment
                   {
                       space_action("environment", player, environments);
                   }         
                   else // space trap
                   {
                       space_action("trap", player, traps);
                   }
                   
                   // update bool
                   keep_game = keep_game(player, board, rounds, stop_game); // keep game 2. updates due to points
               }
               String message_stop_game = "Would you like to stop the game?";
               stop_game = yes_no(message_stop_game);
   
               // update bool
               keep_game = keep_game(player, board, rounds, stop_game); // keep game 3. updates due to stop game
           }
   
           // update final player board
           if (get_player_board(player) > board)
           {
               set_player_board(player, board);
           }
          
           // reasons for end game
           reasons_end_game(player, board, rounds, stop_game);
               
           return;
       } // END of play
   
       // Method (function) to check if onditions to keep playing the game are still valid
       public static boolean keep_game(Player player, int board, int rounds, boolean stop_game)
       {
           boolean keep_game = (get_player_board(player) < board) && (get_player_rounds(player) < rounds) && (get_player_points(player) > 0 && (stop_game == false)); // conditions to keep playing
               
           return keep_game;
       } // END of keep_game
   
       // Method (procedure) to updates the variable round of record player
       public static void update_round(Player player)
       {
           int round = get_player_rounds(player);
           int updated_round = round + 1;
   
           set_player_rounds(player, updated_round);
   
           return;
       } // END of update_round
   
       // Method (procedure) to print the current round and the maximum rounds allowd
       public static void print_round(int max_rounds, int round)
       {
           
           System.out.println("------------------- Round: " + round + " | " + max_rounds + " -------------------");
   
           return;
       } // END of print_round
   
       public static int walking()
       {
           System.out.println("Walking on the forest...");
           
           final int sides_walk = 6;
   
           System.out.println("Rolling dice from 1 to " + sides_walk + " ...");
           
           int to_walk = roll_dice(sides_walk);
   
           System.out.println("Dice rolled! Walk " + to_walk + " spaces.");
           
           return to_walk;
       } // END of walking
   
       // Method (procedure) to updates the variable board of record player
       public static int update_board(int board, int walked)
       {
           int updated_board = board + walked;
   
           return updated_board;
       } // END of update_board
   
       // Method (procedure) to print the variable board of record player
       public static void print_board(int board_size, int previous_board, int board)
       {
           System.out.println("Board: " + previous_board + " | " + board_size + " ----->> " + board + " | " + board_size);
           System.out.println();
           
           return;
       } // END of print_board
   
       // Method (function) Generates a random number that represents the type of step on the board
       public static int board_generator()
       {
           /* 
           Board possibilities
               Animals     - 1, 2
               Plants      - 3, 4
               Enviroments - 5
               Traps       - 6
           */
           final int board_possibilities = 6;
           
           int number = roll_dice(board_possibilities);
   
           return number;
       } // END of board_generator
   
       // Procedure that runs the type of game for each random number generated previously (animal,plants, traps or environments)
       public static void space_action(String type, Player player, Family[] records)
       {
           // message space description
           System.out.println("Space description: " + type);
   
           // index generator
           int record_length = records.length;
           int record_index = roll_dice(record_length) - 1; // subtract -1 because it is an array index
           
           // getting record data
           String record_name = get_family_name(records[record_index]);
           String record_description = get_family_description(records[record_index]);
           int record_points = get_family_points(records[record_index]);
   
           // message record description
           System.out.println("Name: " + record_name
                             + "\nDescription: " + record_description
                             + "\nPoints: " + record_points);
   
           // Update type found
           update_type_found(player, type, record_name); // update quantity and list of type found
           
           // animal
           if (type.equals("animal"))
           {
               space_action_animal(player, record_points);
           }
               
           // plants, environments, trap 
           else
           {
               space_action_non_animal(player, record_points);
           }
               
           return;
       } // END of space_action
   
       // Procedure to updates quantity and list of type found of player
       public static void update_type_found(Player player, String type, String record_name)
       {
           boolean on_list = true; // case true, nothing will be done, so initialize with true
               
           if (type.equals("animal"))
           {
               // update quantity
               set_player_animals(player, get_player_animals(player) + 1);
   
               // update list
               set_player_animals_list(player, update_list(get_player_animals_list(player), record_name));
           }
           else if (type.equals("plant"))
           {
               // update quantity
               set_player_plants(player, get_player_plants(player) + 1);
   
               // update list
               set_player_plants_list(player, update_list(get_player_plants_list(player), record_name));
           }
           else if (type.equals("environment"))
           {
               // update quantity
               set_player_environments(player, get_player_environments(player) + 1);
   
               // update list
               set_player_environments_list(player, update_list(get_player_environments_list(player), record_name));
           }
           else // trap
           {
               // update quantity
               set_player_traps(player, get_player_traps(player) + 1);
   
               // update list
               set_player_traps_list(player, update_list(get_player_traps_list(player), record_name));
           }
           
           return;
       } // END of update_type_found
   
       // Function to updates the list of the type found by adding its name to it
       public static String[] update_list(String[] list, String record_name)
       {
           String[] list_updated = list;
           
           // search first index with null value
           int index_null = search_index_value_null(list);
   
           // search value on list
           boolean on_list = search_on_list(list, index_null, record_name);
   
           // update value on list
           if (on_list == false)
           {
               list_updated[index_null] = record_name;
           }
           return list_updated;
       } // END of update_list
   
       // Function to searchs for the first index with value null
       public static int search_index_value_null(String[] list)
       {
           int i;
           
           for (i = 0; i < list.length; i++)
           {
               String value = list[i];
   
               if (value == null)
               {
                   break;
               }
           }
           
           return i;
       } // END of search_index_value_null
   
       // Function to search on an array if any value on its indexes correspond to the value pass as an argument.
       public static boolean search_on_list(String[] list, int index_null, String record_name)
       {
           boolean on_list = false; 
   
           // search on the list if the name is already on it
           for (int i = 0; i <= index_null; i++)
           {
               String name_on_list =  list[i];
   
               if (name_on_list != null)
               {
                   if (name_on_list.equals("record_name"))
                   {
                       on_list = true;
                       break;
                   }
               }
           }
   
           return on_list;
       } // END of search_on_list
   
       // Method (procedure) that given the player and the necessary number of points to heal animal, tries to heal the animal
       public static void space_action_animal(Player player, int record_points)
       {
           // user option to heal the animal
           String message_try_heal = ("Would you like to try to heal the animal?");
           boolean option = yes_no(message_try_heal);
   
           if (option == false) // not heal
           {
               System.out.println("You chose not to try to heal the animal. Okay, let's continue wandering through the forest.");
           }
   
           else // heal
           {
               System.out.println("You chose to try to heal the animal! Let's try it!");
   
               boolean healed = false;
               boolean user_stop = false;
   
               while ((healed == false) && (user_stop == false) && (player.points > 0))
               {
                   healed = try_heal(player, record_points);
   
                   if ((healed == false) && (player.points > 0))
                   {
                       String message_retry = ("Would you like to retry to heal the animal?");
                       boolean retry = yes_no(message_retry);
                       
                       if (retry == false) // if user does not wish to retry heal the animal, then stop lop
                       {
                           System.out.println("You chose no to retry to heal the animal. Okay, let's continue wandering through the forest. ");
                           user_stop = true;
                       } 
                       else
                       {
                           System.out.println("Okay! let's retry to heal the animal!");
                       }
                   }
               }   
           }
           
           return;
       } // END of space_action_animal
   
       // Method (function) to try heal the animal
       public static boolean try_heal(Player player, int record_points)
       {
           // rolling dice
           System.out.println("Rolling dice 1 to 10...");
           final int size = 10;
           int dice = roll_dice(size);
   
           System.out.println("Necessary points: " + record_points + " | You got: " + dice) ;
   
           boolean healed = false;
           
           // check if healed
           if (dice >= record_points)
           {
               healed = true;
               System.out.println("You healed the animal");
               update_points(player, record_points);
           }
           else
           {
               healed = false;
               System.out.println("You failed to heal the animal");
               update_points(player, (record_points * -1));
           }
   
           return healed;
       } // END of try_heal
   
       // Method procedure to updates the player points after the action on the game
       public static void update_points(Player player, int action_points)
       {
           int previous_points = player.points;
           
           int updated_points = previous_points + action_points;
   
           set_player_points(player, updated_points);
   
           print_updated_points(previous_points, updated_points);
               
           return;
       } // END of update_points
   
       // Procedure that prints the previous and new player points after action
       public static void print_updated_points(int previous_points, int updated_points)
       {
           if (previous_points > updated_points)
           {
               System.out.println("↓ ↓ ↓ Points decreased : " + previous_points + " ----->> " + updated_points);
           }
           else
           {
               System.out.println("↑ ↑ ↑ Points increased : " + previous_points + " ----->> " + updated_points);
           }
               
           System.out.println();
   
           return;
       } // END of print_updated_points
   
       // Procedure to updates the points of the player.
       public static void space_action_non_animal(Player player, int record_points)
       {
           // Update points
           update_points(player, record_points);
   
           return;
       } // END of space_action_non_animal
   
       public static void reasons_end_game(Player player, 
                                           int board, 
                                           int rounds, 
                                           boolean stop_game)
       {
           // reasons for end game
           if (get_player_board(player) >= board)
           {
               System.out.println("----------- You arrived at the end of the Forest -----------"
                              + "\n------------------ The game ends here ----------------------\n");
           }          
   
           if (get_player_rounds(player) > rounds)
           {
               System.out.println("----- You reached the maximum number of rounds allowed -----"
                              + "\n------------------ The game ends here ----------------------\n");
           }  
   
           if (get_player_points(player) < 0)
           {
               System.out.println("------------- You lost all your healing points -------------"
                              + "\n------------------ The game ends here ----------------------\n");    
           }      
   
           if (stop_game == true)
           {
               System.out.println("---------------- You chose to stop the game ------------------"
                              + "\n------------------ The game ends here ----------------------\n");
           }
           return;
       } // END of reasons_end_game
   
       // Method (function) to generate a random value from a given max value.
       public static int roll_dice(int sides)
       {
           Random dice = new Random(); // Create a new random number generator
   
           int dice_number;
           
           dice_number = dice.nextInt(sides) + 1; // A random number using nextInt(n), it generates a random number between 0 (inclusive) and n (exclusive). 
                                                  // This means it can generate values from 0 up to n-1.
                                                  // Therefore, to generate a number from 1 to 6, it must be add +1 (between (0+1) and (6-1+1)) = (between 1 and 6)
           return dice_number;
       } // END of roll_dice
   
       // Load game from previous game where player stop
       public static void load_game(Player player) throws IOException
       {
           String filename = "game_saved.csv";
           BufferedReader inputStream = new BufferedReader (new FileReader(filename));
               
           String text_file = inputStream.readLine();
   
           int line = 1; // each line of file
           
           while(text_file != null)
           {
               String[] player_components = text_file.split(";");
               //System.out.println("Line: " + line);
              
               if (line == 1)
               {
                   set_player_name(player, player_components[1]);
                   //System.out.println("Player name: " + player.name);
               }
               else if (line == 2)
               {
                   set_player_points(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player points: " + player.points);
               }
               else if (line == 3)
               {
                   set_player_board(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player board: " + player.board);
               }
               else if (line == 4)
               {
                   set_player_rounds(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player rounds: " + player.rounds);
               }  
               else if (line == 5)
               {
                   set_player_animals(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player animals: " + player.animals);
               }  
               else if (line == 6)
               {
                   set_player_plants(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player plants: " + player.plants);
               } 
               else if (line == 7)
               {
                   set_player_traps(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player traps: " + player.traps);
               }
               else if (line == 8)
               {
                   set_player_environments(player, Integer.parseInt(player_components[1]));
                   //System.out.println("Player environments: " + player.environments);
               }
               else if (line == 9)
               {
                   //System.out.println("ANIMALS COMPENTS SIZE: " + player_components.length);
                   for (int i = 1; i < player_components.length; i++)
                   {
                       //System.out.println("Component: " + i + ": " + player_components[i]);
                       set_player_animals_list(player, update_list(get_player_animals_list(player), player_components[i]));
                   }
               }      
               else if (line == 10)
               {
                   //System.out.println("PLANTS COMPENTS SIZE: " + player_components.length);
                   for (int i = 1; i < player_components.length; i++)
                   {
                       //System.out.println("Component: " + i + ": " + player_components[i]);
                       set_player_plants_list(player, update_list(get_player_plants_list(player), player_components[i]));
                   }
               }      
               else if (line == 11)
               {
                   //System.out.println("TRAPS COMPENTS SIZE: " + player_components.length);
                   for (int i = 1; i < player_components.length; i++)
                   {
                       //System.out.println("Component: " + i + ": " + player_components[i]);
                       set_player_traps_list(player, update_list(get_player_traps_list(player), player_components[i]));
                   }
               }   
               else if (line == 12)
               {
                  //System.out.println("ENVIRONMENTS COMPENTS SIZE: " + player_components.length);
                   for (int i = 1; i < player_components.length; i++)
                   {
                       //System.out.println("Component: " + i + ": " + player_components[i]);
                       set_player_environments_list(player, update_list(get_player_environments_list(player), player_components[i]));
                   }
               }  
               text_file = inputStream.readLine(); // new line 
               
               line = line + 1; // increment line
               
           }
   
           inputStream.close();
   
           return;
       } // END of load_game
   
       // Method procedure to save current game to be loaded later
       public static void save_game(Player player, int board, int rounds) throws IOException
       {
           /*
           Game can be savd only if the conditions are valid
               1. Player points > 0
               2. Player rounds < max rounds
               3. Player board < max board
           */
   
           boolean can_save = can_save(player, board, rounds);
   
           if (can_save)
           {
               // save game
               boolean user_save_game = false;    
           
               String message_stop_game = "Would you like to save the game?";
               user_save_game = yes_no(message_stop_game);
               
               if (user_save_game)
               {
                   create_file_save_game(player);
               }
           }
   
           return;
       } // END of save_game
   
       // Method function that checks if the conditions to save the game are valid
       public static boolean can_save(Player player, int board, int rounds)
       {
           boolean can_save = true;
   
           if (get_player_board(player) >= board || get_player_rounds(player) > rounds || get_player_points(player) < 0)
           {
               can_save = false;
           }
           
           return can_save;
       } // END of can_save
   
       // Method procedure to create file of the saved game
       public static void create_file_save_game(Player player) throws IOException
       {
           String file_type = "csv";
   
           String file_name = "game_saved";
           
           file_name = file_name + "." + file_type; // csv, txt
   
           PrintWriter file = new PrintWriter (new FileWriter(file_name));
   
           // print accordingly
           print_file_save_game(file, player);
   
           System.out.println(file_name + " was written.");
           
           file.close();
   
           line_break();
   
           return;
       } // END of create_file_save_game
   
       // Method procedure to print data of the player on the save game file
       public static void print_file_save_game(PrintWriter file, Player player) throws IOException
       {
           file.print("name;" + get_player_name(player) + "\n"
                       + "points;" + get_player_points(player) + "\n"
                       + "board;" + get_player_board(player) + "\n"
                       + "rounds;" + get_player_rounds(player) + "\n"
                       + "animals;" + get_player_animals(player) + "\n"
                       + "plants;" + get_player_plants(player) + "\n"
                       + "traps;" + get_player_traps(player) + "\n"
                       + "environments;" + get_player_environments(player) + "\n");
   
           String[][] elements = {
               get_player_animals_list(player), 
               get_player_plants_list(player), 
               get_player_traps_list(player), 
               get_player_environments_list(player)
           };
   
           for (int i = 0; i < elements.length; i++)
           {
               if (i == 0)
               {
                   file.print("animals_list;");
                   print_array(file, elements[i]);
               }
               else if (i == 1)
               {
                   file.print("plants_list;");
                   print_array(file, elements[i]);
               }   
               else if (i == 2)
               {
                   file.print("traps_list;");
                   print_array(file, elements[i]);
               }  
               else if (i == 3)
               {
                   file.print("environments_list;");
                   print_array(file, elements[i]);
               }  
               file.print("\n");
           }
           
           return;
       } // END of print_file_save_game
   
       // Method procedure to print the list of animals, plants, traps and environments that were found on the saved file
       public static void print_array(PrintWriter file, String[] array_of_elements) throws IOException
       {
           for (int i = 0; i < array_of_elements.length; i++)
           {
               if (array_of_elements[i] != null)
               {
                   file.print(array_of_elements[i]);
                   
                   if (i < array_of_elements.length - 1) 
                   {
                       file.print(";");
                   } 
                       
                   else 
                   {
                       file.print("\n");
                   }
               }
           }
               
           return;
       } // END of print_array
   
       // Method procedure to print the results of the game by getting the values of variables of class player
       public static void print_results(Player player)
       {
           System.out.println("------------------ Game Results ------------------ ");
           System.out.println("  Adventurer name: " + get_player_name(player));
           System.out.println("  Board: " + get_player_board(player));
           System.out.println("  Rounds: " + get_player_rounds(player));
   
           System.out.println("------------------ Score ------------------ ");    
           System.out.println("  Final Score: " + get_player_points(player));
   
           System.out.println("------------------ Elements found ------------------ ");    
           System.out.println("  Animals: " + get_player_animals(player));
           System.out.println("  Plants: " + get_player_plants(player));
           System.out.println("  Traps: " + get_player_traps(player));
           System.out.println("  Environments: " + get_player_environments(player));
   
           System.out.println("------------------ Elements found list ------------------ ");  
           String[][] list = {get_player_animals_list(player), get_player_plants_list(player), get_player_traps_list(player), get_player_environments_list(player)};
   
           for (int i = 0; i < list.length; i++)
           {
               if (i == 0)
               {
                   System.out.println("----- ANIMALS -----");
               }
               else if (i == 1)
               {
                   System.out.println("----- PLANTS -----");
               }            
               else if (i == 1)
               {
                   System.out.println("----- TRAPS -----");
               }  
               else
               {
                   System.out.println("----- ENVIRONMENTS -----");
               }  
   
               // listing
               for (int x = 0; x < list[i].length; x++)
               {
                   String name = list[i][x];
   
                   if (name != null)
                   {
                       System.out.println((x + 1) + ": " + name );
                   }
               }
               System.out.println();
           }
           return;
       } // END of print_results
   
       // Method procedure to print the results of the game on a file txt
       public static void print_results_file(Player player) throws IOException
       {
           // Printing score file
           System.out.println("\n -------- Creating file with results -------\n");
   
           PrintWriter file = new PrintWriter (new FileWriter("game_results.txt"));
           
           file.print("------------------ GAME RESULTS ------------------\n");
           file.print("\n  Adventurer name: " + get_player_name(player));
           file.print("\n  Board: " + get_player_board(player));
           file.print("\n  Rounds: " + get_player_rounds(player));
   
           file.print("\n\n------------------ SCORE ------------------ ");    
           file.print("\n  Final Score: " + get_player_points(player));
   
           file.print("\n\n------------------ ELEMENTS FOUND ------------------ ");    
           file.print("\n  Animals: " + get_player_animals(player));
           file.print("\n  Plants: " + get_player_plants(player));
           file.print("\n  Traps: " + get_player_traps(player));
           file.print("\n  Environments: " + get_player_environments(player));
   
           file.print("\n\n------------------ Elements found list ------------------ ");  
           String[][] list = {get_player_animals_list(player), get_player_plants_list(player), get_player_traps_list(player), get_player_environments_list(player)};
   
           for (int i = 0; i < list.length; i++)
           {
               if (i == 0)
               {
                   file.print("\n----- ANIMALS -----");
               }
               else if (i == 1)
               {
                   file.print("\n----- PLANTS -----");
               }            
               else if (i == 1)
               {
                   file.print("\n----- TRAPS -----");
               }  
               else
               {
                   file.print("\n----- ENVIRONMENTS -----");
               }  
   
               // listing
               for (int x = 0; x < list[i].length; x++)
               {
                   String name = list[i][x];
   
                   if (name != null)
                   {
                       file.print("\n" + (x + 1) + ": " + name );
                   }
               }
               file.print("\n");
           }
   
           System.out.println("file created: game_results.txt");
           
           file.close();
   
           return;
       } // END of print_results_file
   
   } // END of Class
   