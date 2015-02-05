using System;
using System.Collections.Generic;
using System.Linq;

namespace Assignment1
{
    public class Digraph<Tvertex, Tvlabel, Telabel>
    {
        private readonly IReadOnlyCollection<Vertex<Tvertex, Tvlabel>> _vertices;
        private readonly IReadOnlyCollection<Edge<Telabel, Tvertex, Tvlabel>> _edges;
        private readonly Func<Edge<Telabel, Tvertex, Tvlabel>, Vertex<Tvertex, Tvlabel>, Vertex<Tvertex, Tvlabel>, bool> 
            _edgeFilter = (e, v1, v2) => e.Source.Identifier.Equals(v1.Identifier) && e.Destination.Identifier.Equals(v2.Identifier);

        public Digraph() : this(new List<Vertex<Tvertex, Tvlabel>>(), new List<Edge<Telabel, Tvertex, Tvlabel>>()) { }


        // This is to return G' made the ctor private to avoid implementation details leaking out
        private Digraph(IEnumerable<Vertex<Tvertex, Tvlabel>> vertices, IEnumerable<Edge<Telabel, Tvertex, Tvlabel>> edges)
        {
            _vertices = vertices.ToList().AsReadOnly();
            _edges = edges.ToList().AsReadOnly();
        }

        public bool IsEmpty{ get { return _vertices.Count == 0; } }

        public Digraph<Tvertex, Tvlabel, Telabel> AddVertex(Vertex<Tvertex, Tvlabel> vertex, Tvlabel label)
        {
            if (_vertices.Any(v => v.Identifier.Equals(vertex.Identifier))) throw new ArgumentException(string.Format("Vertex Id {0} must be unique", vertex.Identifier));

            var vertexToAdd = new Vertex<Tvertex, Tvlabel>(vertex.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Add(vertexToAdd);

            return new Digraph<Tvertex, Tvlabel, Telabel>(updatedVerticesList, _edges);
        }

        public Digraph<Tvertex, Tvlabel, Telabel> RemoveVertex(Vertex<Tvertex, Tvlabel> vertex)
        {
            var vertexToRemove = this.GetVertex(vertex.Identifier);
            if (vertexToRemove == null) throw new ArgumentException(FormatMissingVertexExceptionMessage(vertex.Identifier));

            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToRemove);

            return new Digraph<Tvertex, Tvlabel, Telabel>(updatedVerticesList, _edges);
        }


        public Digraph<Tvertex, Tvlabel, Telabel> UpdateVertex(Vertex<Tvertex, Tvlabel> vertex, Tvlabel label)
        {
            var vertexToUpdate = this.GetVertex(vertex.Identifier);
            if (vertexToUpdate == null) throw new ArgumentException(FormatMissingVertexExceptionMessage(vertex.Identifier));

            var updatedVertex = new Vertex<Tvertex, Tvlabel>(vertexToUpdate.Identifier, label);
            var updatedVerticesList = _vertices.ToList();
            updatedVerticesList.Remove(vertexToUpdate);
            updatedVerticesList.Add(updatedVertex);

            return new Digraph<Tvertex, Tvlabel, Telabel>(updatedVerticesList, _edges);
        }

        public Tvlabel GetVertex(Vertex<Tvertex, Tvlabel> vertex)
        {
            var vertexToGet = this.GetVertex(vertex.Identifier);
            if (vertexToGet == null) throw new ArgumentException(FormatMissingVertexExceptionMessage(vertex.Identifier));

            return vertexToGet.Label;
        }

        public bool HasVertex(Vertex<Tvertex, Tvlabel> vertex)
        {
            return this.GetVertex(vertex.Identifier) != null;
        }

        public IEnumerable<Vertex<Tvertex, Tvlabel>> AllVertices()
        {
            return _vertices.ToList();
        }

        public Digraph<Tvertex, Tvlabel, Telabel> AddEdge(Vertex<Tvertex, Tvlabel> vertex1, Vertex<Tvertex, Tvlabel> vertex2, Telabel label)
        {
            var source = this.GetVertex(vertex1.Identifier);
            if (source == null)  throw new ArgumentException(FormatMissingVertexExceptionMessage(vertex1.Identifier));

            var destination = this.GetVertex(vertex2.Identifier);
            if (destination == null) throw new ArgumentException(FormatMissingVertexExceptionMessage(vertex2.Identifier));
            
            var edge = new Edge<Telabel, Tvertex, Tvlabel>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Add(edge);

            return new Digraph<Tvertex, Tvlabel, Telabel>(_vertices, updatedEdgeList);

        }

        public Digraph<Tvertex, Tvlabel, Telabel> RemoveEdge(Vertex<Tvertex, Tvlabel> vertex1, Vertex<Tvertex, Tvlabel> vertex2)
        {
            var edgeToRemove = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            if (edgeToRemove == null) throw new ArgumentException(FormatMissingEdgeExceptionMessage(vertex1.Identifier, vertex2.Identifier));

            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToRemove);

            return new Digraph<Tvertex, Tvlabel, Telabel>(_vertices, updatedEdgeList);
        }

        public Digraph<Tvertex, Tvlabel, Telabel> UpdateEdge(Vertex<Tvertex, Tvlabel> vertex1, Vertex<Tvertex, Tvlabel> vertex2, Telabel label)
        {
            var edgeToUpdate = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            if (edgeToUpdate == null) throw new ArgumentException(FormatMissingEdgeExceptionMessage(vertex1.Identifier, vertex2.Identifier));

            var updatedEdge = new Edge<Telabel, Tvertex, Tvlabel>(label, vertex1, vertex2);
            var updatedEdgeList = _edges.ToList();
            updatedEdgeList.Remove(edgeToUpdate);
            updatedEdgeList.Add(updatedEdge);

            return new Digraph<Tvertex, Tvlabel, Telabel>(_vertices, updatedEdgeList);
        }

        public Telabel GetEdge(Vertex<Tvertex, Tvlabel> vertex1, Vertex<Tvertex, Tvlabel> vertex2)
        {
            var edge = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));
            if (edge == null) throw new ArgumentException(FormatMissingEdgeExceptionMessage(vertex1.Identifier, vertex2.Identifier));

            return edge.Label;
        }

        public bool HasEdge(Vertex<Tvertex, Tvlabel> vertex1, Vertex<Tvertex, Tvlabel> vertex2)
        {
            return _edges.Any(e => _edgeFilter(e, vertex1, vertex2));
        }

        public IEnumerable<Vertex<Tvertex, Tvlabel>> FromEdges(Vertex<Tvertex, Tvlabel> vertex)
        {
            return _edges
                .Where(e => e.Source.Identifier.Equals(vertex.Identifier))
                .Select(e => e.Destination)
                .ToList();
        }

        private Vertex<Tvertex, Tvlabel> GetVertex(Tvertex id)
        {
            var vertexToGet = _vertices.SingleOrDefault(v => v.Identifier.Equals(id));
            return vertexToGet;
        }
 
        private string FormatMissingVertexExceptionMessage(Tvertex vertexIdentifier)
        {
            return string.Format("Vertex Id {0} is not an element within this graph's vertex set", vertexIdentifier);
        }

        private string FormatMissingEdgeExceptionMessage(Tvertex v1Id, Tvertex v2Id)
        {
            return string.Format("Edge from vertex {0} to {1} is not an element within this graph's edge set", v1Id, v2Id);
        }
    }
}
