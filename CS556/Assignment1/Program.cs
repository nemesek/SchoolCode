using System;
using System.Linq;

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
            Console.WriteLine(Update_Vertex_ChangesLabelOnVertexAndReturnsGPrime()); // True
            Console.WriteLine(Get_Vertex_ReturnsTheLabelFromVertexVInGraphG()); // True
            Console.WriteLine(Has_Vertex_ReturnsTrueWhenVertexVIsInGraphG());   // True
            Console.WriteLine(Has_Vertex_ReturnsFalseWhenVertexVIsNotInGraphG()); // True
            Console.WriteLine(Add_Edge_InsertsEdgeFromV1ToV2AndReturnsGPrime());    // True
            Console.WriteLine(Remove_Edge_DeletesEdgeFromGraphGAndReturnsGPrime());   // True
            Console.WriteLine(Update_Edge_ChangesEdgeLabelAndReturnsGPrime());  // True
            Console.WriteLine(Get_Edge_ReturnsTheEdgeLabel());  // True
            Console.WriteLine(Has_Edge_ReturnsTrueWhenEdgeFromV1ToV2InGraphG());    // True
            Console.WriteLine(Has_Edge_ReturnsFalseWhenEdgeFromV1ToV2NotInGraphG());    // True
            Console.WriteLine(All_Vertices_ReturnsEmptyWhenGraphHasNoVertices());   // True
            Console.WriteLine(All_Vertices_ReturnsEnumerableOfAllVertices());   // True
            Console.WriteLine(From_Edges_ReturnsEmptyWhenVertexHasNoDirectSuccessors());    // True
            Console.WriteLine(From_Edges_ReturnsEnumerableOfAllSuccessorsForVertexV()); // True
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
            return graphPrime.GetVertex(vertex).Equals(expectedLabel);
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

        static bool Update_Vertex_ChangesLabelOnVertexAndReturnsGPrime()
        {
            // Arrange preconditions
            var graph = new Digraph<int, object, int>();
            var vertex = new Vertex<int, object>(1);
            var object1 = new object();
            var graphPrime = graph.AddVertex(vertex,object1);

            // Act
            var object2 = new object();
            var graphDoublePrime = graphPrime.UpdateVertex(vertex, object2);

            // Assert postconditions
            if(graphDoublePrime == graphPrime) return false;
            if(graphDoublePrime.GetVertex(vertex) == object1) return false;
            return graphDoublePrime.GetVertex(vertex) == object2;
        }

        static bool Get_Vertex_ReturnsTheLabelFromVertexVInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "Label1";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.GetVertex(vertex);

            // Assert postconditions
            if (result != label) return false;
            return graphPrime != graph;
        }

        static bool Has_Vertex_ReturnsTrueWhenVertexVIsInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "HasVertex";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.HasVertex(vertex);

            // Assert postconditions
            return result;
        }

        static bool Has_Vertex_ReturnsFalseWhenVertexVIsNotInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "HasVertex";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.HasVertex(new Vertex<string, string>("Id2"));

            // Assert postconditions
            return !result;
        }

        static bool Add_Edge_InsertsEdgeFromV1ToV2AndReturnsGPrime()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            
            // Act
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);

            // Assert postconditions
            if (graphTriplePrime == graphDoublePrime) return false;
            if (!graphTriplePrime.FromEdges(vertex1).Any()) return false;
            if (graphTriplePrime.FromEdges(vertex2).Any()) return false;
            return graphTriplePrime.GetEdge(vertex1, vertex2) == edgeLabel;
        }

        static bool Remove_Edge_DeletesEdgeFromGraphGAndReturnsGPrime()
        {
            // Arrange preconditons
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);

            // Act
            var graphQuadruplePrime = graphTriplePrime.RemoveEdge(vertex1, vertex2);

            // Assert postconditions
            if (graphQuadruplePrime == graphTriplePrime) return false;
            return !graphQuadruplePrime.HasEdge(vertex1,vertex2);
        }
       
        static bool Update_Edge_ChangesEdgeLabelAndReturnsGPrime()
        {
            // Arrange preconditons
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);

            // Act
            var updatedEdgeLabel = 2;
            var graphQuadruplePrime = graphTriplePrime.UpdateEdge(vertex1, vertex2, updatedEdgeLabel);

            // Assert
            if (graphQuadruplePrime == graphTriplePrime) return false;
            if (graphQuadruplePrime.GetEdge(vertex1, vertex2) == edgeLabel) return false;
            return graphQuadruplePrime.GetEdge(vertex1, vertex2) == updatedEdgeLabel;
        }

        static bool Get_Edge_ReturnsTheEdgeLabel()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);

            // Act
            var label = graphTriplePrime.GetEdge(vertex1, vertex2);

            // Assert postconditons
            return label == edgeLabel;
        }

        static bool Has_Edge_ReturnsTrueWhenEdgeFromV1ToV2InGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);

            // Act
            var result = graphTriplePrime.HasEdge(vertex1, vertex2);

            // Assert postconditions
            return !graphDoublePrime.HasEdge(vertex1, vertex2) && result;
        }

        static bool Has_Edge_ReturnsFalseWhenEdgeFromV1ToV2NotInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            
            // Act
            var result = graph.HasEdge(vertex1, vertex2);

            // Assert postconditions
            return !result;
        }

        static bool All_Vertices_ReturnsEmptyWhenGraphHasNoVertices()
        {
            // Arrange preconditons
            var graph = new Digraph<int, int, int>();
            
            // Act
            var result = graph.AllVertices();

            // Assert
            return !result.Any();
        }

        static bool All_Vertices_ReturnsEnumerableOfAllVertices()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);

            // Act
            var vertices = graphDoublePrime.AllVertices();
            
            // Assert postconditons
            if (vertices.Count() != 2) return false;
            return vertices.Any(v => v.Identifier == vertex1.Identifier) && vertices.Any(v => v.Identifier == vertex2.Identifier);
        }

        static bool From_Edges_ReturnsEmptyWhenVertexHasNoDirectSuccessors()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);

            // Act
            var successors = graphPrime.FromEdges(vertex1);

            // Assert postconditions
            return !successors.Any();
        }

        static bool From_Edges_ReturnsEnumerableOfAllSuccessorsForVertexV()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);
            var edgeLabel = 1;
            var graphTriplePrime = graphDoublePrime.AddEdge(vertex1, vertex2, edgeLabel);
            var vertex3 = new Vertex<int, int>(3);
            var graphQuadruplePrime = graphTriplePrime.AddVertex(vertex3, vertex3.Identifier);
            var graphQuintuplePrime = graphQuadruplePrime.AddEdge(vertex1, vertex3, edgeLabel + 1);

            // Act
            var successors = graphQuintuplePrime.FromEdges(vertex1);

            // Assert postconditions
            if (successors.Count() != 2) return false;
            return successors.Any(v => v.Identifier == vertex2.Identifier) && successors.Any(v => v.Identifier == vertex3.Identifier);
        }
    }
}
