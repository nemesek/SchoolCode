using System;
using System.Linq;

namespace Assignment1
{
    class Program
    {
        private const string Pass = "PASS";
        private const string Fail = "FAIL";

        static void Main(string[] args)
        {
            Console.WriteLine("===============new_graph test:===============");
            Console.WriteLine(New_Graph_CreatesAndReturnsANewInstanceOfTheGraphADT());  // PASS
            Console.WriteLine("===============is_empty tests:===============");
            Console.WriteLine(Is_Empty_ReturnsTrueWhenGraphHasNoVertices());    // PASS
            Console.WriteLine(Is_Empty_ReturnsFalseWhenGraphHasAtLeastOneVertex()); // PASS
            Console.WriteLine("===============add_vertex tests:===============");
            Console.WriteLine(Add_Vertex_InsertsVertexWithLabelAndReturnsGPrime()); // PASS
            Console.WriteLine(Add_Vertex_ThrowsExceptionWhenVertexIdIsNotUnique()); // PASS
            Console.WriteLine("===============remove_vertex tests:===============");
            Console.WriteLine(Remove_Vertex_DeletesVertexAndReturnsGPrime());   // PASS
            Console.WriteLine(Remove_Vertex_ThrowsExceptionWhenVertexIsNotInGraph());   // PASS
            Console.WriteLine("===============update_vertex tests:===============");
            Console.WriteLine(Update_Vertex_ChangesLabelOnVertexAndReturnsGPrime()); // PASS
            Console.WriteLine(Update_Vertex_ThowsExceptionWhenVertexIsNotInGraph());    // PASS
            Console.WriteLine("===============get_vertex tests:===============");
            Console.WriteLine(Get_Vertex_ReturnsTheLabelFromVertexVInGraphG()); // PASS
            Console.WriteLine(Get_Vertex_ThrowsExceptionWhenVertexNotInGraph());    // PASS
            Console.WriteLine("===============has_vertex tests:===============");
            Console.WriteLine(Has_Vertex_ReturnsTrueWhenVertexVIsInGraphG());   // PASS
            Console.WriteLine(Has_Vertex_ReturnsFalseWhenVertexVIsNotInGraphG()); // PASS
            Console.WriteLine("===============add_edge tests:===============");
            Console.WriteLine(Add_Edge_InsertsEdgeFromV1ToV2AndReturnsGPrime());    // PASS
            Console.WriteLine(Add_Edge_ThrowsExceptionWhenEitherVertexNotInGraph());    // PASS
            Console.WriteLine("===============remove_edge tests:===============");
            Console.WriteLine(Remove_Edge_DeletesEdgeFromGraphGAndReturnsGPrime());   // PASS
            Console.WriteLine(Remove_Edge_ThrowsExceptionWhenEdgeIsNotInGraph());   // PASS
            Console.WriteLine("===============update_edge tests:===============");
            Console.WriteLine(Update_Edge_ChangesEdgeLabelAndReturnsGPrime());  // PASS
            Console.WriteLine(Update_Edge_ThrowsExceptionWhenEdgeNotInGraph()); // PASS
            Console.WriteLine("===============get_edge tests:===============");
            Console.WriteLine(Get_Edge_ReturnsTheEdgeLabel());  // PASS
            Console.WriteLine(Get_Edge_ThrowsExceptionWhenEdgeNotInGraph());    // PASS
            Console.WriteLine("===============has_edge tests:===============");
            Console.WriteLine(Has_Edge_ReturnsTrueWhenEdgeFromV1ToV2InGraphG());    // PASS
            Console.WriteLine(Has_Edge_ReturnsFalseWhenEdgeFromV1ToV2NotInGraphG());    // PASS
            Console.WriteLine("===============all_vertices tests:===============");
            Console.WriteLine(All_Vertices_ReturnsEmptyWhenGraphHasNoVertices());   // PASS
            Console.WriteLine(All_Vertices_ReturnsEnumerableOfAllVertices());   // PASS
            Console.WriteLine("===============from_edges tests:===============");
            Console.WriteLine(From_Edges_ReturnsEmptyWhenVertexHasNoDirectSuccessors());    // PASS
            Console.WriteLine(From_Edges_ReturnsEnumerableOfAllSuccessorsForVertexV()); // PASS
        }

        static string New_Graph_CreatesAndReturnsANewInstanceOfTheGraphADT()
        {
            // Arrange Preconditions

            // Act
            var graph = new Digraph<int, int, int>();

            // Assert
            return graph != null ? Pass : Fail;
        }

        static string Is_Empty_ReturnsTrueWhenGraphHasNoVertices()
        {
            // Arrange Preconditions
            var graph = new Digraph<int, int, int>();

            // Act
            var result = graph.IsEmpty;

            // Assert Postconditions
            return result ? Pass : Fail;
        }

        static string Is_Empty_ReturnsFalseWhenGraphHasAtLeastOneVertex()
        {
            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            var graphPrime = graph.AddVertex(vertex, "Foo");

            // Act
            var result = graphPrime.IsEmpty;

            // Assert Postconditions
            return !result ? Pass : Fail;
        }

        static string Add_Vertex_InsertsVertexWithLabelAndReturnsGPrime()
        {
            var expectedLabel = "Foo";

            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);

            // Act
            var graphPrime = graph.AddVertex(vertex, expectedLabel);

            // Assert Postconditions
            if (graphPrime == graph) return Fail;
            if (graphPrime.IsEmpty) return Fail;
            if (!graph.IsEmpty) return Fail;
            if (!graphPrime.HasVertex(vertex)) return Fail;
            return graphPrime.GetVertex(vertex).Equals(expectedLabel) ? Pass:Fail;
        }

        static string Add_Vertex_ThrowsExceptionWhenVertexIdIsNotUnique()
        {
            var expectedMessage = "Vertex Id 1 must be unique";

            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            var graphPrime = graph.AddVertex(vertex, "Foo");
            
            // Act 
            bool result;

            try
            {
                graphPrime.AddVertex(vertex, "Bar");
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage == ex.Message;
            }

            return result ? Pass:Fail;
        }

        static string Remove_Vertex_DeletesVertexAndReturnsGPrime()
        {
            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            var graphPrime = graph.AddVertex(vertex, "Foo");
            

            // Act
            var graphDoublePrime = graphPrime.RemoveVertex(vertex);

            // Assert Postconditions
            if (graphDoublePrime == graphPrime) return Fail;
            if (!graphPrime.GetVertex(vertex).Equals("Foo")) return Fail;
            if (!graphDoublePrime.IsEmpty) return Fail;
            if (!graphPrime.HasVertex(vertex)) return Fail;
            return !graphDoublePrime.HasVertex(vertex) ? Pass:Fail;
        }

        static string Remove_Vertex_ThrowsExceptionWhenVertexIsNotInGraph()
        {
            var expectedMessage = "Vertex Id 1 is not an element within V";

            // Arrange Preconditions
            var graph = new Digraph<int, string, int>();
            var vertex = new Vertex<int, string>(1);
            
            // Act 
            var result = false;
            try
            {
                var graphPrime = graph.RemoveVertex(vertex);
                return Fail;
            }
            catch (ArgumentException ae)
            {
                result = ae.Message == expectedMessage;
            }

            return result ? Pass : Fail;
        }

        static string Update_Vertex_ChangesLabelOnVertexAndReturnsGPrime()
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
            if(graphDoublePrime == graphPrime) return Fail;
            if(graphDoublePrime.GetVertex(vertex) == object1) return Fail;
            return graphDoublePrime.GetVertex(vertex) == object2 ? Pass : Fail;
        }

        static string Update_Vertex_ThowsExceptionWhenVertexIsNotInGraph()
        {
            var expectedMessage = "Vertex Id Id1 is not an element within V";

            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "Label1";

            // Act 
            bool result;

            try
            {
                graph.UpdateVertex(vertex, "Foo");
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage == ex.Message;
            }

            return result ? Pass : Fail;

        }

        static string Get_Vertex_ReturnsTheLabelFromVertexVInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "Label1";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.GetVertex(vertex);

            // Assert postconditions
            if (result != label) return Fail;
            return graphPrime != graph ? Pass : Fail;
        }

        static string Get_Vertex_ThrowsExceptionWhenVertexNotInGraph()
        {
            var expectedMessage = "Vertex Id 1 is not an element within V";

            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex = new Vertex<int, int>(1);
            
            // Act
            bool result;
            try
            {
                graph.GetVertex(vertex);
                return Fail;
            }
            catch (ArgumentException ae)
            {
                result = ae.Message == expectedMessage;
            }

            return result ? Pass : Fail;
        }



        static string Has_Vertex_ReturnsTrueWhenVertexVIsInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "HasVertex";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.HasVertex(vertex);

            // Assert postconditions
            return result ? Pass : Fail;
        }

        static string Has_Vertex_ReturnsFalseWhenVertexVIsNotInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<string, string, string>();
            var vertex = new Vertex<string, string>("Id1");
            var label = "HasVertex";
            var graphPrime = graph.AddVertex(vertex, label);

            // Act
            var result = graphPrime.HasVertex(new Vertex<string, string>("Id2"));

            // Assert postconditions
            return !result ? Pass : Fail;
        }

        static string Add_Edge_InsertsEdgeFromV1ToV2AndReturnsGPrime()
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
            if (graphTriplePrime == graphDoublePrime) return Fail;
            if (!graphTriplePrime.FromEdges(vertex1).Any()) return Fail;
            if (graphTriplePrime.FromEdges(vertex2).Any()) return Fail;
            return graphTriplePrime.GetEdge(vertex1, vertex2) == edgeLabel ? Pass : Fail;
        }

        static string Add_Edge_ThrowsExceptionWhenEitherVertexNotInGraph()
        {
            var expectedMessage1 = "Vertex Id 1 is not an element within V";
            var expectedMessage2 = "Vertex Id 2 is not an element within V";
            

            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);

            // Act
            bool result;

            try
            {
                graph.AddEdge(vertex1, vertex2, 1);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage1 == ex.Message;
            }

            try
            {
                var graphPrime = graph.AddVertex(vertex2, vertex2.Identifier);
                graphPrime.AddEdge(vertex1, vertex2, 1);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage1 == ex.Message;
            }

            try
            {
                var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
                graphPrime.AddEdge(vertex1, vertex2, 1);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage2 == ex.Message;
            }

            return result ? Pass : Fail;
        }

        static string Remove_Edge_DeletesEdgeFromGraphGAndReturnsGPrime()
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
            if (graphQuadruplePrime == graphTriplePrime) return Fail;
            return !graphQuadruplePrime.HasEdge(vertex1,vertex2) ? Pass : Fail;
        }

        static string Remove_Edge_ThrowsExceptionWhenEdgeIsNotInGraph()
        {
            var expectedMessage = "Edge from vertex 1 to 2 is not an element within E";
            
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);

            // Act
            bool result;

            try
            {
                graphDoublePrime.RemoveEdge(vertex1, vertex2);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage == ex.Message;
            }

            return result ? Pass : Fail;

        }
       
        static string Update_Edge_ChangesEdgeLabelAndReturnsGPrime()
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
            if (graphQuadruplePrime == graphTriplePrime) return Fail;
            if (graphQuadruplePrime.GetEdge(vertex1, vertex2) == edgeLabel) return Fail;
            return graphQuadruplePrime.GetEdge(vertex1, vertex2) == updatedEdgeLabel ? Pass : Fail;
        }

        static string Update_Edge_ThrowsExceptionWhenEdgeNotInGraph()
        {
            var expectedMessage = "Edge from vertex 1 to 2 is not an element within E";

            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);

            // Act
            bool result;

            try
            {
                graphDoublePrime.UpdateEdge(vertex1, vertex2, 5);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage == ex.Message;
            }

            return result ? Pass : Fail;
        }

        static string Get_Edge_ReturnsTheEdgeLabel()
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
            return label == edgeLabel ? Pass : Fail;
        }

        static string Get_Edge_ThrowsExceptionWhenEdgeNotInGraph()
        {
            var expectedMessage = "Edge from vertex 1 to 2 is not an element within E";

            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);
            var graphDoublePrime = graphPrime.AddVertex(vertex2, vertex2.Identifier);

            // Act
            bool result;

            try
            {
                graphDoublePrime.GetEdge(vertex1, vertex2);
                return Fail;
            }
            catch (Exception ex)
            {
                result = expectedMessage == ex.Message;
            }

            return result ? Pass : Fail;
        }

        static string Has_Edge_ReturnsTrueWhenEdgeFromV1ToV2InGraphG()
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
            return !graphDoublePrime.HasEdge(vertex1, vertex2) && result ? Pass : Fail;
        }

        static string Has_Edge_ReturnsFalseWhenEdgeFromV1ToV2NotInGraphG()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var vertex2 = new Vertex<int, int>(2);
            
            // Act
            var result = graph.HasEdge(vertex1, vertex2);

            // Assert postconditions
            return !result ? Pass : Fail;
        }

        static string All_Vertices_ReturnsEmptyWhenGraphHasNoVertices()
        {
            // Arrange preconditons
            var graph = new Digraph<int, int, int>();
            
            // Act
            var result = graph.AllVertices();

            // Assert
            return !result.Any() ? Pass : Fail;
        }

        static string All_Vertices_ReturnsEnumerableOfAllVertices()
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
            if (vertices.Count() != 2) return Fail;
            return vertices.Any(v => v.Identifier == vertex1.Identifier) && vertices.Any(v => v.Identifier == vertex2.Identifier) ? Pass : Fail;
        }

        static string From_Edges_ReturnsEmptyWhenVertexHasNoDirectSuccessors()
        {
            // Arrange preconditions
            var graph = new Digraph<int, int, int>();
            var vertex1 = new Vertex<int, int>(1);
            var graphPrime = graph.AddVertex(vertex1, vertex1.Identifier);

            // Act
            var successors = graphPrime.FromEdges(vertex1);

            // Assert postconditions
            return !successors.Any() ? Pass : Fail;
        }

        static string From_Edges_ReturnsEnumerableOfAllSuccessorsForVertexV()
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
            if (successors.Count() != 2) return Fail;
            return successors.Any(v => v.Identifier == vertex2.Identifier) && successors.Any(v => v.Identifier == vertex3.Identifier) ? Pass : Fail;
        }
    }
}
