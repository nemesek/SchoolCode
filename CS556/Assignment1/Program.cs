using System;

namespace Assignment1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine(New_Graph_CreatesAndReturnsANewInstanceOfTheGraphADT());  // True
            Console.WriteLine(Is_Empty_ReturnsTrueWhenGraphHasNoVertices());    // True
            Console.WriteLine(Is_Empty_ReturnsFalseWhenGraphHasAtLeastOneVertex()); // True
            Console.WriteLine(Add_Vertex_InsertsVertexWithLabelAndReturnsGPrime()); // True
            Console.WriteLine(Remove_Vertex_DeletesVertexAndReturnsGPrime());   // True
        }

        static bool New_Graph_CreatesAndReturnsANewInstanceOfTheGraphADT()
        {
            // Arrange Preconditions

            // Act
            var graph = new Digraph<int, int, int>();

            // Assert
            return graph != null;
        }

        static bool Is_Empty_ReturnsTrueWhenGraphHasNoVertices()
        {
            // Arrange Preconditions
            var graph = new Digraph<int, int, int>();

            // Act
            var result = graph.IsEmpty;

            // Assert Postconditions
            return result;
        }

        static bool Is_Empty_ReturnsFalseWhenGraphHasAtLeastOneVertex()
        {
            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            var graphPrime = graph.AddVertex(vertex, "Hey");

            // Act
            var result = graphPrime.IsEmpty;

            // Assert Postconditions
            return !result;
        }

        static bool Add_Vertex_InsertsVertexWithLabelAndReturnsGPrime()
        {
            var expectedLabel = "Hey";

            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);

            // Act
            var graphPrime = graph.AddVertex(vertex, expectedLabel);

            // Assert Postconditions
            if (graphPrime == graph) return false;
            if (graphPrime.IsEmpty) return false;
            if (!graph.IsEmpty) return false;
            if (!graphPrime.HasVertex(vertex)) return false;
            if (!graphPrime.GetVertex(vertex).Equals(expectedLabel)) return false;
            
            return true;
        }

        static bool Remove_Vertex_DeletesVertexAndReturnsGPrime()
        {
            var expectedMessage = "Vertex Id 1 is not an element within V";

            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            var graphPrime = graph.AddVertex(vertex, "Hey");
            

            // Act
            var graphDoublePrime = graphPrime.RemoveVertex(vertex);

            // Assert Postconditions
            if (graphDoublePrime == graphPrime) return false;
            if (!graphPrime.GetVertex(vertex).Equals("Hey")) return false;
            if (!graphDoublePrime.IsEmpty) return false;
            if (!graphPrime.HasVertex(vertex)) return false;
            if (graphDoublePrime.HasVertex(vertex)) return false;

            var result = false;
            try
            {
                graphDoublePrime.GetVertex(vertex);
                return result;
            }
            catch(ArgumentException ae)
            {
                result = ae.Message == expectedMessage;
            }           

            return result;
        }
    }
}
