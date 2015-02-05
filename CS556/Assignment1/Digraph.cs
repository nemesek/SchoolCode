using System;
using System.Collections.Generic;
using System.Linq;

namespace Assignment1
{
    public class Digraph<V,L,E>
    {
        private readonly IReadOnlyCollection<Vertex<V,L>> _vertices;
        private readonly IReadOnlyCollection<Edge<E,V,L>> _edges;
        private readonly Func<Edge<E,V,L>, Vertex<V,L>, Vertex<V,L>, bool> _edgeFilter = (e, v1, v2) => e.Source.Identifier.Equals(v1.Identifier) && e.Destination.Identifier.Equals(v2.Identifier);
        private readonly Func<V,string> missingVertexExceptionMessageFunc = id => string.Format("Vertex Id {0} is not an element within V", id);
        private readonly Func<V,V,string> missingEdgeExceptionMessageFunc = (v1, v2) => string.Format("Edge from vertex {0} to {1} is not an element within E", v1, v2);
        private readonly Func<IReadOnlyCollection<Vertex<V,L>>,V,Vertex<V,L>> getVertexByIdFunc = (set,id) => set.SingleOrDefault(v => v.Identifier.Equals(id));

        public Digraph() : this(new List<Vertex<V,L>>(), new List<Edge<E,V,L>>()) { }


        // This is to return G' made the ctor private to avoid implementation details leaking out
        private Digraph(IEnumerable<Vertex<V,L>> vertices, IEnumerable<Edge<E,V,L>> edges)
        {
            _vertices = vertices.ToList().AsReadOnly();
            _edges = edges.ToList().AsReadOnly();
        }

        public bool IsEmpty{ get { return _vertices.Count == 0; } }

        public Digraph<V,L,E> AddVertex(Vertex<V,L> vertex, L label)
        {
            if (_vertices.Any(v => v.Identifier.Equals(vertex.Identifier))) throw new ArgumentException(string.Format("Vertex Id {0} must be unique", vertex.Identifier));

            var vertexToAdd = new Vertex<V,L>(vertex.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Add(vertexToAdd);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        public Digraph<V,L,E> RemoveVertex(Vertex<V,L> vertex)
        {
            var vertexToRemove = getVertexByIdFunc(_vertices,vertex.Identifier);
            if (vertexToRemove == null) throw new ArgumentException(missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToRemove);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }


        public Digraph<V,L,E> UpdateVertex(Vertex<V,L> vertex, L label)
        {
            var vertexToUpdate = getVertexByIdFunc(_vertices, vertex.Identifier);
            if (vertexToUpdate == null) throw new ArgumentException(missingVertexExceptionMessageFunc(vertex.Identifier));

            var updatedVertex = new Vertex<V,L>(vertexToUpdate.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToUpdate);
            updatedVerticesList.Add(updatedVertex);

            return new Digraph<V,L,E>(updatedVerticesList, _edges);
        }

        public L GetVertex(Vertex<V,L> vertex)
        {
            var vertexToGet = getVertexByIdFunc(_vertices, vertex.Identifier);
            if (vertexToGet == null) throw new ArgumentException(missingVertexExceptionMessageFunc(vertex.Identifier));

            return vertexToGet.Label;
        }

        public bool HasVertex(Vertex<V,L> vertex)
        {
            return getVertexByIdFunc(_vertices, vertex.Identifier) != null;
        }

        public IEnumerable<Vertex<V,L>> AllVertices()
        {
            return _vertices.ToList();
        }

        public Digraph<V,L,E> AddEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            var source = getVertexByIdFunc(_vertices,vertex1.Identifier);
            if (source == null) throw new ArgumentException(missingVertexExceptionMessageFunc(vertex1.Identifier));

            var destination = getVertexByIdFunc(_vertices,vertex2.Identifier);
            if (destination == null) throw new ArgumentException(missingVertexExceptionMessageFunc(vertex2.Identifier));
            
            var edge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Add(edge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);

        }

        public Digraph<V,L,E> RemoveEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            var edgeToRemove = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));

            if (edgeToRemove == null) throw new ArgumentException(missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToRemove);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        public Digraph<V,L,E> UpdateEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2, E label)
        {
            var edgeToUpdate = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));

            if (edgeToUpdate == null) throw new ArgumentException(missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            var updatedEdge = new Edge<E,V,L>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToUpdate);
            updatedEdgeList.Add(updatedEdge);

            return new Digraph<V,L,E>(_vertices, updatedEdgeList);
        }

        public E GetEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            var edge = _edges.SingleOrDefault(e => _edgeFilter(e,vertex1, vertex2));
            if (edge == null) throw new ArgumentException(missingEdgeExceptionMessageFunc(vertex1.Identifier, vertex2.Identifier));

            return edge.Label;
        }

        public bool HasEdge(Vertex<V,L> vertex1, Vertex<V,L> vertex2)
        {
            return _edges.Any(e => _edgeFilter(e,vertex1, vertex2));
        }

        public IEnumerable<Vertex<V,L>> FromEdges(Vertex<V,L> vertex)
        {
            return _edges
                .Where(e => e.Source.Identifier.Equals(vertex.Identifier))
                .Select(e => e.Destination)
                .ToList();
        }

    }
}
