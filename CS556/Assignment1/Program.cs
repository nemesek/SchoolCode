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
            Console.WriteLine(graph.IsEmpty);
            graph.RemoveVertex(vertex);
            Console.WriteLine(graph.IsEmpty);
            var vertex2 = new Vertex<int>();
            graph.AddVertex(vertex2, 7);
            graph.UpdateVertex(vertex2, 6);
            Console.WriteLine(graph.GetVertex(vertex2));
            Console.WriteLine(graph.HasVertex(new Vertex<int>() { Label = 4 }));
            Console.WriteLine(graph.HasVertex(new Vertex<int>() { Label = vertex2.Label }));
            graph.AddVertex(new Vertex<int>() { Label = 2 }, 2);

            var vertices = graph.AllVertices();

            foreach(var v in vertices)
            {
                Console.WriteLine(v.Label);
            }

            var vertex3 = new Vertex<int>(){ Label = 2 };
            graph.AddEdge(vertex2, vertex3);
            Console.WriteLine(graph.HasEdge(vertex2, vertex3));
            Console.WriteLine(graph.HasEdge(vertex3, vertex2));

        }
    }
}
