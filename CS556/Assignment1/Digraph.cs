using System;
using System.Collections.Generic;
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

        // vertex, edge predicates
        private readonly Func<IReadOnlyCollection<Vertex<V, L>>, V, Vertex<V, L>> _vertexFilter = (set, id) => set.SingleOrDefault(v => v.Identifier.Equals(id));
        private readonly Func<Edge<E,V,L>, Vertex<V,L>, Vertex<V,L>, bool> _edgeFilter = (e, v1, v2) => e.DirectPredecessor.Identifier.Equals(v1.Identifier) && e.DirectSuccessor.Identifier.Equals(v2.Identifier);
        
        // exception message builders
        private readonly Func<V,string> _missingVertexExceptionMessageFunc = id => string.Format("Vertex Id {0} is not an element within V", id);
        private readonly Func<V,V,string> _missingEdgeExceptionMessageFunc = (v1, v2) => string.Format("Edge from vertex {0} to {1} is not an element within E", v1, v2);
        

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
        // throws exception if vertex v already exists in V
        public Digraph<V,L,E> AddVertex(Vertex<V,L> vertex, L label)
        {
            if (_vertices.Any(v => v.Identifier.Equals(vertex.Identifier))) throw new ArgumentException(string.Format("Vertex Id {0} must be unique", vertex.Identifier));

            var vertexToAdd = new Vertex<V,L>(vertex.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Add(vertexToAdd);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // creates V' = V - v
        // constructs G'=(V',E) and returns G'
        // throws exception if v not in V
        public Digraph<V,L,E> RemoveVertex(Vertex<V,L> vertex)
        {
            var vertexToRemove = _vertexFilter(_vertices,vertex.Identifier);
            if (vertexToRemove == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToRemove);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // creates V' = (V - v) + v'
        // constructs G'=(V',E) and returns G'
        // throws exception if v not in V
        public Digraph<V,L,E> UpdateVertex(Vertex<V,L> vertex, L label)
        {
            var vertexToUpdate = _vertexFilter(_vertices, vertex.Identifier);
            if (vertexToUpdate == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVertex = new Vertex<V,L>(vertexToUpdate.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToUpdate);
            updatedVerticesList.Add(updatedVertex);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        // Returns vertex v label where v in V 
        // Throws exception if v not in V
        public L GetVertex(Vertex<V,L> vertex)
        {
            var vertexToGet = _vertexFilter(_vertices, vertex.Identifier);
            if (vertexToGet == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex.Identifier));

            return vertexToGet.Label;
        }

        // Returns true if v in V otherwise false
        public bool HasVertex(Vertex<V,L> vertex)
        {
            return _vertexFilter(_vertices, vertex.Identifier) != null;
        }

        // Returns V
        public IEnumerable<Vertex<V,L>> AllVertices()
        {
            return _vertices.ToList();
        }

        // creates E' = E + e
        // constructs G' = (V, E') and returns G'
        // throws exception if v1 not in V || v2 not in V
        public Digraph<V,L,E> AddEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            var source = _vertexFilter(_vertices,vertex1.Identifier);
            if (source == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex1.Identifier));

            var destination = _vertexFilter(_vertices,vertex2.Identifier);
            if (destination == null) throw new ArgumentException(_missingVertexExceptionMessageFunc(vertex2.Identifier));
            
            var edge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Add(edge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);

        }

        // creates E' = E - e
        // constructs G' = (V, E') and returns G'
        // throws exception if e not in E
        public Digraph<V,L,E> RemoveEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            var edgeToRemove = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));

            if (edgeToRemove == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToRemove);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        // creates E' = (E - e) + e'
        // constructs G' = (V, E') and returns G'
        // throws exception if e not in E
        public Digraph<V,L,E> UpdateEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            var edgeToUpdate = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));

            if (edgeToUpdate == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToUpdate);
            updatedEdgeList.Add(updatedEdge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        // returns edge e label where e in E && e = (v1,v2)
        // throws exception if e not in E 
        public E GetEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            var edge = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));
            if (edge == null) throw new ArgumentException(_missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            return edge.Label;
        }

        // returns true if e in E otherwise false
        public bool HasEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            return _edges.Any(e => _edgeFilter(e,vertex1, vertex2));
        }

        // returns all directSuccessors of v
        public IEnumerable<Vertex<V,L>> FromEdges(Vertex<V,L> vertex)
        {
            return _edges
                .Where(e => e.DirectPredecessor.Identifier.Equals(vertex.Identifier))
                .Select(e => e.DirectSuccessor)
                .ToList();
        }

        public override string ToString()
        {
            //return base.ToString();
            var builder = new StringBuilder();

            foreach (var v in _vertices)
            {
                _buildPredecessor(v, builder);

                foreach (var p in FromEdges(v))
                {
                    _buildSucessor(p, builder);
                }

            }

            return builder.ToString();
        }

        private Action<Vertex<V, L>, StringBuilder> _buildPredecessor = (v,b) =>
            {
                b.Append("Vertex with Id ");
                b.Append(v.Identifier.ToString());
                b.Append(" is Direct Predecessor of: ");
                b.AppendLine();
            };

        private Action<Vertex<V, L>, StringBuilder> _buildSucessor = (v, b) =>
            {
                b.Append("----Successor Vertex with Id ");
                b.Append(v.Identifier);
                b.AppendLine();
            };


    }
}
