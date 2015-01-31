using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignment1
{
    class Program
    {
        static void Main(string[] args)
        {
            var graph = new Graph<int>();
            var vertex = new Vertex<int>();
            graph.AddVertex(vertex, 5);
        }
    }
}
