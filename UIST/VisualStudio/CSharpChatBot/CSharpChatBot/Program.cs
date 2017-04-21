using System;
using System.Collections.Generic;

namespace CSharpChatBot
{
    struct Notebook
    {
        String Name;
        List<String> Entries;
        //The enum which were needed
        public enum NotebookCommand
        {
            SAVE_ENTRY,
            PRINT_ALL
        }
        //Konstructor for the Struct
        public Notebook(String w, List<String> a)
        {
            this.Name = w;
            this.Entries = a;
           

        }
        //Saves a entry in the List
        public void SaveEntry(String w)
        {
            
            Entries.Add(String.Format("{0} saved in {1}", w, DateTime.Now));

        }
        //Will print all entries
        public void PrintEntries()
        {
            Console.WriteLine("Notebook\n");
            foreach(var i in Entries)
            {
                Console.WriteLine("{0}",i);
            }
        }
        
    }
    class Program

    { 
        //Main method
        static void Main(string[] args)
        {
            //Reading in the name of the user
            Console.Write("Enter your name to get started!\n");
            var name= "";
            Notebook.NotebookCommand nCommand;
            
            while((name = Console.ReadLine()) == String.Empty)
            {
                Console.WriteLine("No Name was Entered!\nPlease try again!");
            }
            //new instance of Notebook
            Notebook book = new Notebook(name, new List<string>());
           
            /*
             * Saving the Dictionary Strings
             */
            Dictionary<String, String> dic = new Dictionary<string, string>();
            dic.Add("TELL_DATE", String.Format("Todays Date is {0}", DateTime.Now));
            dic.Add("TELL_JOKE", "Why can't Java Developers wear glasses? Because they don't C#!");
            dic.Add("SAY_HI", String.Format("Hello {0}, nice too meet you!", name));
            
            //Here the Commands will be printed
            Console.WriteLine("Enter a command!");
            foreach(KeyValuePair<String, String> pair in dic)
            {
                Console.WriteLine(pair.Key);
            }
            Console.WriteLine("SAVE_ENTRY");
            Console.WriteLine("PRINT_ALL");
            var input = "";
            //Loop starts here, first check if the input isn´t Empty. If so..Loop will stop
            while ((input = Console.ReadLine()) != String.Empty)
            {
                
                if (dic.ContainsKey(input))
                {
                    Console.WriteLine(dic[input]);
                }
                else
                {
                    //Splitting the String at :
                    string[] splitString = input.Split(':');
                    if(Notebook.NotebookCommand.TryParse(splitString[0], out nCommand))
                    {
                        //Switch starts here.
                        switch (nCommand)
                        {
                            case Notebook.NotebookCommand.PRINT_ALL:
                                book.PrintEntries();
                                break;
                            case Notebook.NotebookCommand.SAVE_ENTRY:
                                if (splitString.Length == 2 && !string.IsNullOrEmpty(splitString[1]))
                                {
                                    
                                    book.SaveEntry(splitString[1]);
                                }
                                break;
                        }
                    }
                    
                }

            }
            {

            }

        }
    }
}
