using System;
using System.Collections.Generic;
using System.Linq;

namespace Assignment1
{
    public class Digraph<T>
    {
        private readonly List<Vertex<T>> _vertices = new List<Vertex<T>>();
        private readonly List<Edge<T>> _edges = new List<Edge<T>>();
        private readonly Func<Edge<T>, Vertex<T>, Vertex<T>, bool> _edgeFilter = (e, v1, v2) => e.Source.Label.Equals(v1.Label) && e.Destination.Label.Equals(v2.Label);
        public Digraph() {}

        public bool IsEmpty
        {
            get { return _vertices.Count == 0; }

        }

        public Digraph<T> AddVertex(Vertex<T> vertex, T label)
        {
            vertex.Label = label;
            // todo: check for dupes if that is a requirement
            vertex.Label = label;
            _vertices.Add(vertex);
            return this;
        }

        public Digraph<T> RemoveVertex(Vertex<T> vertex)
        {
             var vertexToRemove = this.GetVertex(vertex.Label);
            if (vertexToRemove != null) _vertices.Remove(vertexToRemove);
            
            return this;
        }


        public Digraph<T> UpdateVertex(Vertex<T> vertex, T label)
        {
            var vertexToUpdate = this.GetVertex(vertex.Label);
            if (vertexToUpdate != null) vertexToUpdate.Label = label;
            return this;
        }

        public T GetVertex(Vertex<T> vertex)
        {
            var vertexToGet = this.GetVertex(vertex.Label);
            return vertexToGet == null ? default(T) : vertexToGet.Label;
        }

        public bool HasVertex(Vertex<T> vertex)
        {
            return this.GetVertex(vertex.Label) != null;
        }

        public IEnumerable<Vertex<T>> AllVertices()
        {
            return _vertices.ToList();
        }

        public Digraph<T> AddEdge(Vertex<T> vertex1, Vertex<T> vertex2, T label)
        {
            var source = this.GetVertex(vertex1.Label);
            if (source == null) return this;    // todo: figure out what to return

            var destination = this.GetVertex(vertex2.Label);
            if (destination == null) return this;

            var edge = new Edge<T> { Source = source, Destination = destination, Label = label };
            _edges.Add(edge);
            return this;
            
        }

        public Digraph<T> RemoveEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
             var edgeToRemove = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1,vertex2));

             if (edgeToRemove != null) _edges.Remove(edgeToRemove);
             return this;
        }

        public Digraph<T> UpdateEdge(Vertex<T> vertex1, Vertex<T> vertex2, T label)
        {
            var edgeToUpdate = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            if(edgeToUpdate != null) edgeToUpdate.Label = label;
            return this;        
         }

        public T GetEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            var edge = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            return edge != null ? edge.Label : default(T);
        }

        public bool HasEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            return _edges.Any(e => _edgeFilter(e, vertex1, vertex2));            
        }

        public IEnumerable<Vertex<T>> FromEdges(Vertex<T> vertex)
        {
            return _edges
                .Where(e => e.Source.Label.Equals(vertex.Label))
                .Select(e => e.Destination)
                .ToList();
        }

        private Vertex<T> GetVertex(T label)
        {
            var vertexToGet = _vertices.SingleOrDefault(v => v.Label.Equals(label));

            return vertexToGet;
        }     
    }
}
