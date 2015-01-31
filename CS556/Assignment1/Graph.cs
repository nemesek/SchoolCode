using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Linq;

namespace Assignment1
{   
    // Do the labels have to be unique for vertices?
    // If the labels aren't unique how do we know if a vertex passed in as an arg to HasVertex is the same?  By Reference?  By its neighbors?
    // What does it mean for an edge to have a label?
    public class Graph<T>
    {
        private List<Tuple<Vertex<T>, List<Vertex<T>>>> _adjacencyList = new List<Tuple<Vertex<T>, List<Vertex<T>>>>();
        public Graph() {}

        public bool IsEmpty
        {
            get { return _adjacencyList.Count == 0; }

        }

        public Graph<T> AddVertex(Vertex<T> vertex, T label)
        {
            vertex.Label = label;
            // todo: check for dupes if that is a requirement
            var tupleToAdd = new Tuple<Vertex<T>, List<Vertex<T>>>(vertex, new List<Vertex<T>>());
            _adjacencyList.Add(tupleToAdd);
            return this;
        }

        public Graph<T> RemoveVertex(Vertex<T> vertex)
        {
             var vertexToRemove = this.GetVertexTuple(vertex.Label);
            if (vertexToRemove != null) _adjacencyList.Remove(vertexToRemove);
            
            return this;
        }


        public Graph<T> UpdateVertex(Vertex<T> vertex, T label)
        {
            var vertexToUpdate = this.GetVertexTuple(vertex.Label);
            if (vertexToUpdate != null) vertexToUpdate.Item1.Label = label;
            return this;
        }

        public T GetVertex(Vertex<T> vertex)
        {
            var vertexToGet = this.GetVertexTuple(vertex.Label);
            if (vertexToGet == null) return default(T);
            return vertexToGet.Item1.Label;

        }

        public bool HasVertex(Vertex<T> vertex)
        {
            return this.GetVertexTuple(vertex.Label) != null ? true : false;
        }

        public IEnumerable<Vertex<T>> AllVertices()
        {
            return _adjacencyList
                .Select(t => t.Item1)
                .ToList();
        }

        public Graph<T> AddEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            var source = this.GetVertexTuple(vertex1.Label);
            if (source == null) return this;    // todo: figure out what to return

            var destination = this.GetVertexTuple(vertex2.Label);
            if (destination == null) return this;

            source.Item2.Add(vertex2);
            return this;
            
        }

        public bool HasEdge(Vertex<T> vertex1, Vertex<T> vertex2)
        {
            var source = this.GetVertexTuple(vertex1.Label);
            if(source == null) return false;

            var destination = this.GetVertexTuple(vertex2.Label);
            if (destination == null) return false;

            return source.Item2.Where(v => v.Label.Equals(vertex2.Label)).Count() > 0;
        }

        private Tuple<Vertex<T>, List<Vertex<T>>> GetVertexTuple(T label)
        {
            var vertexToGet = _adjacencyList
                .Where(t => t.Item1.Label.Equals(label))
                .SingleOrDefault();

            return vertexToGet;
        }

        
    }
}
