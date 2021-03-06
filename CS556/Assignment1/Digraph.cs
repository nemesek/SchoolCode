﻿using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;

namespace Assignment1
{
    // V = Vertex Identifier, L = Vertex Label,  E = Edge Label
    // The IConvertible Constraint forces V to be of type Boolean, Byte, Char, DateTime, Decimal, Double
    // Int (16, 32 and 64-bit), SByte, Single (float), String, or UInt (16, 32 and 64-bit)
    // Without the constraint equality checks against reference types that don't override Equals
    // would not work since Vertex implementation is immutable and therefore the equality by ref would fail
    public class Digraph<V,L,E> where V : IConvertible
    {
        // sets
        private readonly IReadOnlyCollection<Vertex<V, L>> _vertices;
        private readonly IReadOnlyCollection<Edge<E,V,L>> _edges;

        // vertex and edge predicates
        private readonly Func<IReadOnlyCollection<Vertex<V, L>>, V, Vertex<V, L>> _getVertexByIdFunc = (set, id) => set.SingleOrDefault(v => v.Identifier.Equals(id));
        private readonly Func<Edge<E,V,L>, Vertex<V,L>, Vertex<V,L>, bool> _getEdgeByVerticesFunc = (e, v1, v2) => e.DirectPredecessor.Identifier.Equals(v1.Identifier) && e.DirectSuccessor.Identifier.Equals(v2.Identifier);
        
        // exception message builders
        private readonly Func<V,string> _missingVertexExceptionMessageFunc = id => string.Format("Vertex Id {0} is not an element within V", id);
        private readonly Func<V,V,string> _missingEdgeExceptionMessageFunc = (v1, v2) => string.Format("Edge from vertex {0} to {1} is not an element within E", v1, v2);

        // ToString actions
        private static readonly Action<Vertex<V, L>, StringBuilder> BuildVertexString = (v, b) =>
        {
            b.Append("Vertex with Id ");
            b.Append(v.Identifier.ToString(CultureInfo.InvariantCulture));
            b.Append(" is Direct Predecessor of: ");
            b.AppendLine();
        };

        private static readonly Action<Vertex<V, L>, StringBuilder> BuildSuccessorsString = (v, b) =>
        {
            b.Append("----Successor Vertex with Id ");
            b.Append(v.Identifier);
            b.AppendLine();
        };

        private static readonly Action<StringBuilder> BuildEmptySuccessorsString = b =>
        {
            b.Append("----No other vertices");
            b.AppendLine();
        };

        // This Action combines various functions to generate string output for printing the vertex info and 
        // the info of the vertex's direct successors
        private static readonly Action<Vertex<V, L>, StringBuilder, Func<Vertex<V,L>, IEnumerable<Vertex<V,L>>>> ComposeVertexToString = (v, b, filter) =>
        {
            {
                BuildVertexString(v, b);
                var successors = filter(v);

                if (successors.Any())
                {
                    Array.ForEach(successors.ToArray(), s => BuildSuccessorsString(s, b));
                    return;
                }

                BuildEmptySuccessorsString(b);
            }

        };

        public Digraph() : this(new List<Vertex<V,L>>(), new List<Edge<E,V,L>>()) { }

        // This is to return G' made the ctor private to avoid implementation details leaking out
        private Digraph(IEnumerable<Vertex<V,L>> vertices, IEnumerable<Edge<E,V,L>> edges)
        {
            // ensure invariant with guard clauses, these should never be null since the ctor is private
            if (vertices == null) throw new ArgumentNullException("vertices");
            if (edges == null) throw new ArgumentNullException("edges");

            _vertices = vertices.ToList().AsReadOnly();
            _edges = edges.ToList().AsReadOnly();
        }

        // returns true if V = {} otherwise false
        public bool IsEmpty{ get { return _vertices.Count == 0; } }

        // creates V' = V + v
        // constructs G'=(V',E) and returns G'
        // throws exception if vertex v already exists in V or if v is null
        public Digraph<V,L,E> AddVertex(Vertex<V,L> vertex, L label)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");
            if (_vertices.Any(v => v.Identifier.Equals(vertex.Identifier))) throw new ArgumentException(string.Format("Vertex Id {0} must be unique", vertex.Identifier));

            var vertexToAdd = new Vertex<V,L>(vertex.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Add(vertexToAdd);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // creates V' = V - v
        // constructs G'=(V',E) and returns G'
        // throws exception if v not in V or if v is null
        public Digraph<V,L,E> RemoveVertex(Vertex<V,L> vertex)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");
            var vertexToRemove = _getVertexByIdFunc(_vertices,vertex.Identifier);
            if (vertexToRemove == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToRemove);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // creates V' = (V - v) + v'
        // constructs G'=(V',E) and returns G'
        // throws exception if v not in V or if v is null
        public Digraph<V,L,E> UpdateVertex(Vertex<V,L> vertex, L label)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");
            var vertexToUpdate = _getVertexByIdFunc(_vertices, vertex.Identifier);
            if (vertexToUpdate == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVertex = new Vertex<V,L>(vertexToUpdate.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToUpdate);
            updatedVerticesList.Add(updatedVertex);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // Returns vertex v label where v in V 
        // Throws exception if v not in V or if v is null
        public L GetVertex(Vertex<V,L> vertex)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");
            var vertexToGet = _getVertexByIdFunc(_vertices, vertex.Identifier);
            if (vertexToGet == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            return vertexToGet.Label;
        }

        // Returns true if v in V otherwise false
        // throws exception if v is null
        public bool HasVertex(Vertex<V,L> vertex)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");
            return _getVertexByIdFunc(_vertices, vertex.Identifier) != null;
        }

        // Returns V
        public IEnumerable<Vertex<V,L>> AllVertices()
        {
            return _vertices.ToList();
        }

        // creates E' = E + e
        // constructs G' = (V, E') and returns G'
        // throws exception if v1 not in V || v2 not in V or if v1 is null or if v2 is null
        public Digraph<V,L,E> AddEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            if (vertex1 == null) throw new ArgumentNullException("vertex1");
            if (vertex2 == null) throw new ArgumentNullException("vertex2");

            var source = _getVertexByIdFunc(_vertices,vertex1.Identifier);
            if (source == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex1.Identifier));

            var destination = _getVertexByIdFunc(_vertices,vertex2.Identifier);
            if (destination == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex2.Identifier));
            
            var edge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Add(edge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);

        }

        // creates E' = E - e
        // constructs G' = (V, E') and returns G'
        // throws exception if e not in E or if v1 is null or if v2 is null
        public Digraph<V,L,E> RemoveEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            if (vertex1 == null) throw new ArgumentNullException("vertex1");
            if (vertex2 == null) throw new ArgumentNullException("vertex2");

            var edgeToRemove = _edges.SingleOrDefault(e => _getEdgeByVerticesFunc(e,vertex1, vertex2));

            if (edgeToRemove == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToRemove);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        // creates E' = (E - e) + e'
        // constructs G' = (V, E') and returns G'
        // throws exception if e not in E or if v1 is null or if v2 is null
        public Digraph<V,L,E> UpdateEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            if (vertex1 == null) throw new ArgumentNullException("vertex1");
            if (vertex2 == null) throw new ArgumentNullException("vertex2");

            var edgeToUpdate = _edges.SingleOrDefault(e => _getEdgeByVerticesFunc(e,vertex1, vertex2));

            if (edgeToUpdate == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToUpdate);
            updatedEdgeList.Add(updatedEdge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        // returns edge e label where e in E && e = (v1,v2)
        // throws exception if e not in E or if v1 is null or if v2 is null
        public E GetEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            if (vertex1 == null) throw new ArgumentNullException("vertex1");
            if (vertex2 == null) throw new ArgumentNullException("vertex2");

            var edge = _edges.SingleOrDefault(e => _getEdgeByVerticesFunc(e,vertex1, vertex2));
            if (edge == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            return edge.Label;
        }

        // returns true if e in E otherwise false
        // throws exception if v1 is null or if v2 is null
        public bool HasEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            if (vertex1 == null) throw new ArgumentNullException("vertex1");
            if (vertex2 == null) throw new ArgumentNullException("vertex2");
            return _edges.Any(e => _getEdgeByVerticesFunc(e,vertex1, vertex2));
        }

        // returns all directSuccessors of v
        // throws exception if v is null
        public IEnumerable<Vertex<V,L>> FromEdges(Vertex<V,L> vertex)
        {
            if (vertex == null) throw new ArgumentNullException("vertex");

            return _edges
                .Where(e => e.DirectPredecessor.Identifier.Equals(vertex.Identifier))
                .Select(e => e.DirectSuccessor)
                .ToList();
        }

        // Overrides ToString
        // Prints out count of vertices and edges
        // Prints out all Vertices v identifiers as well as all of v's successor vertex identifiers
        public override string ToString()
        {
            var builder = new StringBuilder();
            builder.AppendFormat("Graph G has {0} vertices in set V", _vertices.Count());
            builder.AppendLine();
            builder.AppendFormat("Graph G has {0} edges in set E", _edges.Count());
            builder.AppendLine();
            Array.ForEach(_vertices.ToArray(), v => ComposeVertexToString(v, builder, this.FromEdges));
            return builder.ToString();
        }
    }
}
