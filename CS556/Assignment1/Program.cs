using System;

namespace Assignment1
{
    class Program
    {
        static void Main(string[] args)
        {
            var graph = new Digraph<int,int,int>();
            var vertex = new Vertex<int, int>() { Identifier = 1 };
            graph.AddVertex(vertex, 5);
            Console.WriteLine(graph.IsEmpty);  // False
            graph.RemoveVertex(vertex);
            Console.WriteLine(graph.IsEmpty); // True
            var vertex2 = new Vertex<int, int>() { Identifier = 2 };
            graph.AddVertex(vertex2, 7);
            graph.UpdateVertex(vertex2, 6);
            Console.WriteLine(graph.GetVertex(vertex2)); // 6
            Console.WriteLine(graph.HasVertex(new Vertex<int,int>() { Label = 4 })); // False
            Console.WriteLine(graph.HasVertex(new Vertex<int,int>() { Identifier = vertex2.Identifier })); // True
            graph.AddVertex(new Vertex<int,int>() { Identifier = 3 }, 2);

            var vertices = graph.AllVertices();

            foreach (var v in vertices)
            {
                Console.WriteLine(v.Label); //6, 2
            }

            var vertex3 = new Vertex<int,int>() { Identifier = 4 };
            graph.AddVertex(vertex3, 12);
            graph.AddEdge(vertex2, vertex3, 8);
            Console.WriteLine(graph.HasEdge(vertex2, vertex3)); // True
            Console.WriteLine(graph.HasEdge(vertex3, vertex2)); // False
            Console.WriteLine(graph.GetEdge(vertex2, vertex3));  // 8
            graph.UpdateEdge(vertex2, vertex3, 11);

            var vertex4 = new Vertex<int,int>() { Identifier = 5,Label = 22 };
            var vertex5 = new Vertex<int,int>() { Identifier = 6,Label = 23 };
            graph.AddVertex(vertex4, vertex4.Label);
            graph.AddVertex(vertex5, vertex5.Label);

            graph.AddEdge(vertex2, vertex4, 33);
            graph.AddEdge(vertex2, vertex5, 34);
            Console.WriteLine(graph.GetEdge(vertex2, vertex3)); // 11
            //graph.FromEdges(vertex2).Select(v => Console.WriteLine(graph.GetVertex(v)));
            var neighbors = graph.FromEdges(vertex2);
            foreach (var n in neighbors)
            {
                Console.WriteLine(graph.GetVertex(n)); // 11, 22, 23
            }
            graph.RemoveEdge(vertex2, vertex3);
            Console.WriteLine(graph.HasEdge(vertex2, vertex3));  //False
        }
    }
}
