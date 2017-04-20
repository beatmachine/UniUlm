using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSharpChatBot
{
    class Program

    { 
        static void Main(string[] args)
        {
            var name = Console.ReadLine();
            

            Dictionary<String, String> dic = new Dictionary<string, string>();
            dic.Add("TELL_DATE", String.Format("Todays Date is {0}", DateTime.Now));
            dic.Add("TELL_JOKE", "Why can't Java Developers wear glasses? Because they don't C#!");
            dic.Add("SAY_HI", String.Format("Hello {0}, nice too meet you!", name));

            Console.WriteLine("Enter a command!");
            foreach(KeyValuePair<String, String> pair in dic)
            {
                Console.WriteLine(pair.Key);
            }
            var input = "";
            while ((input = Console.ReadLine()) != String.Empty)
            {
                
                if (dic.ContainsKey(input))
                {
                    Console.WriteLine(dic[input]);
                }
                else
                {
                    Console.WriteLine("THIS ISN'T A LEGIT COMMAND");
                }

            }
            {

            }

        }
    }
}
