using System;
using System.Collections;
using System.Collections.Generic;

namespace Excercise1
{
    public enum Language
    {
        CSHARP,
        FUCKYOU,
        BITCH = 5000
    }


    class Program
    {
        static void Main(string[] args)
        {   //Initiating some Lists
            int[] arr2 = { 1, 2, 3, 4, 5 };
            List<string> stringList = new List<string>();
            stringList.Add("Fuckyou");
            stringList.Add("HAHAHAHAHA");
            stringList.Add("BLAALÖSKDöl");
            //Iterate the Items
            IEnumerator enumartor = stringList.GetEnumerator();
            Dictionary<int, object> somedic = new Dictionary<int, object>();
            somedic.Add(1234, "niggnogg");
            somedic.Add(2222, new DateTime());

            while(enumartor.MoveNext())
            {
                Console.WriteLine("List item : {0}", enumartor.Current.ToString());
            }

            foreach(var i in arr2)
            {
                Console.Write("This is the number {0}\n", i);
                 
            }
            foreach(KeyValuePair<int, object> s in somedic)
            {
                Console.Write("Here you {0} go {1}\n", s.Key, s.Value.GetType());
            }
            Console.ReadLine();
        }

    }
}
