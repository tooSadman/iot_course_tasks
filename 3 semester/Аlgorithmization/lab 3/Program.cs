using System;
using System.Collections.Generic;
using System.Linq;

namespace Mykola2
{
public class Program
{
public static void ArrangeMembers(int member1, int member2, ICollection<HashSet<int> > tribes)
{
        var tribe1 = new HashSet<int>(); //плем'я яке містить 1-го мембера'
        var tribe2 = new HashSet<int>(); //плем'я яке містить 2-го мембера'

        // запам'ятовуємо плем'я
        foreach (var tribe in tribes)
        {
                if (tribe.Contains(member1))
                        tribe1 = tribe;
        }

        foreach (var tribe in tribes)
        {
                if (tribe.Contains(member2))
                        tribe2 = tribe;
        }

        //Both members still not arranged
        if (tribe1.Count == 0 && tribe2.Count == 0)
        {
                tribes.Add(new HashSet<int>(100)
                                {
                                        member1,
                                        member2
                                });

                return;
        }

        //Member1 not arranged
        if (tribe1.Count == 0)
        {
                tribe2.Add(member1);

                return;
        }

        //Member2 not arranged
        if (tribe2.Count == 0)
        {
                tribe1.Add(member2);

                return;
        }

        //Both members arranged but they are in different sequenses
        if (!tribe1.Equals(tribe2))
        {
                tribe1.UnionWith(tribe2);

                tribes.Remove(tribe2);
        }
}

public static int CountPairs(ICollection<HashSet<int> > tribes)
{
        //0 - women, 1 - men
        var members = new int[tribes.Count, 2];
        var l=0; //numer of men

        //Count and sort females and males from each tribe
        var i = 0;
        foreach (var tribe in tribes)
        {
                members[i, 0] = tribe.Count(x => x % 2 == 0);
                members[i, 1] = tribe.Count - members[i, 0];
                l += members[i, 1];
                i++;
        }

        var pairsCount = 0;
        for (int j=0; j < tribes.Count; j++)
        {

            pairsCount += members[j,0] * (l - members[j,1]);

        }

        return pairsCount;
}

public static void Main()
{
        var count = Convert.ToInt16(Console.ReadLine());
        var tribes = new List<HashSet<int> >();

        for (int i = 0; i < count; i++)
        {
                var nums = Console.ReadLine().Split(' ').Select(x => Convert.ToInt32(x));

                ArrangeMembers(nums.First(), nums.Last(), tribes);
        }

        Console.WriteLine(CountPairs(tribes));

      //  System.Threading.Thread.Sleep(10000);
}
}
}
