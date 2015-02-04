using System;
using System.Collections.Generic;
using System.Linq;

namespace Assignment1
{
    public class Digraph<Tvertex, Tvlabel, Telabel>
    {
        private readonly List<Vertex<Tvertex, Tvlabel>> _vertices = new List<Vertex<Tvertex,Tvlabel>>();
        private readonly List<Edge<Telabel, Tvertex,Tvlabel>> _edges = new List<Edge<Telabel, Tvertex, Tvlabel>>();
        private readonly Func<Edge<Telabel, Tvertex, Tvlabel>, Vertex<Tvertex, Tvlabel>, Vertex<Tvertex, Tvlabel>, bool> _edgeFilter = (e, v1, v2) => e.Source.Label.Equals(v1.Label) && e.Destination.Label.Equals(v2.Label);
        public Digraph() {}

        public bool IsEmpty
        {
            get { return _vertices.Count == 0; }

        }

        public Digraph<Tvertex, Tvlabel, Telabel> AddVertex(Vertex<Tvertex, Tvlabel> vertex, Tvlabel label)
        {
            vertex.Label = label;
            // todo: check for dupes if that is a requirement
            vertex.Label = label;
            _vertices.Add(vertex);
            return this;
        }

        public Digraph<Tvertex, Tvlabel, Telabel> RemoveVertex(Vertex<Tvertex, Tvlabel> vertex)
        {
             var vertexToRemove = this.GetVertex(vertex.Label);
            if (vertexToRemove != null) _vertices.Remove(vertexToRemove);
            
            return this;
        }


        public Digraph<Tvertex, Tvlabel, Telabel> UpdateVertex(Vertex<Tvertex, Tvlabel> vertex, Tvlabel label)
        {
            var vertexToUpdate = this.GetVertex(vertex.Label);
            if (vertexToUpdate != null) vertexToUpdate.Label = label;
            return this;
        }

        public Tvlabel GetVertex(Vertex<Tvertex, Tvlabel> vertex)
        {
            var vertexToGet = this.GetVertex(vertex.Label);
            return vertexToGet == null ? default(Tvlabel) : vertexToGet.Label;
        }

        public bool HasVertex(Vertex<Tvertex,Tvlabel> vertex)
        {
            return this.GetVertex(vertex.Label) != null;
        }

        public IEnumerable<Vertex<Tvertex,Tvlabel>> AllVertices()
        {
            return _vertices.ToList();
        }

        public Digraph<Tvertex, Tvlabel, Telabel> AddEdge(Vertex<Tvertex,Tvlabel> vertex1, Vertex<Tvertex,Tvlabel> vertex2, Telabel label)
        {
            var source = this.GetVertex(vertex1.Label);
            if (source == null) return this;    // todo: figure out what to return

            var destination = this.GetVertex(vertex2.Label);
            if (destination == null) return this;

            var edge = new Edge<Telabel,Tvertex,Tvlabel> { Source = source, Destination = destination, Label = label };
            _edges.Add(edge);
            return this;
            
        }

        public Digraph<Tvertex, Tvlabel, Telabel> RemoveEdge(Vertex<Tvertex,Tvlabel> vertex1, Vertex<Tvertex,Tvlabel> vertex2)
        {
             var edgeToRemove = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1,vertex2));

             if (edgeToRemove != null) _edges.Remove(edgeToRemove);
             return this;
        }

        public Digraph<Tvertex, Tvlabel, Telabel> UpdateEdge(Vertex<Tvertex,Tvlabel> vertex1, Vertex<Tvertex,Tvlabel> vertex2, Telabel label)
        {
            var edgeToUpdate = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            if(edgeToUpdate != null) edgeToUpdate.Label = label;
            return this;        
         }

        public Telabel GetEdge(Vertex<Tvertex,Tvlabel> vertex1, Vertex<Tvertex,Tvlabel> vertex2)
        {
            var edge = _edges.SingleOrDefault(e => _edgeFilter(e, vertex1, vertex2));

            return edge != null ? edge.Label : default(Telabel);
        }

        public bool HasEdge(Vertex<Tvertex,Tvlabel> vertex1, Vertex<Tvertex,Tvlabel> vertex2)
        {
            return _edges.Any(e => _edgeFilter(e, vertex1, vertex2));            
        }

        public IEnumerable<Vertex<Tvertex,Tvlabel>> FromEdges(Vertex<Tvertex,Tvlabel> vertex)
        {
            return _edges
                .Where(e => e.Source.Label.Equals(vertex.Label))
                .Select(e => e.Destination)
                .ToList();
        }

        private Vertex<Tvertex,Tvlabel> GetVertex(Tvlabel label)
        {
            var vertexToGet = _vertices.SingleOrDefault(v => v.Label.Equals(label));

            return vertexToGet;
        }     
    }
}
