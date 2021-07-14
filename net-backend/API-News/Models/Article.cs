using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Models
{
    public class Article
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Summary { get; set; } 
        public string Description { get; set; }
        public string Imagepath { get; set; }
        public int Status { get; set; }
        public int LikeArticle { get; set; }
        public int SaveArticle { get; set; }
        public int IdCategory { get; set; }
        [ForeignKey("IdCategory")]
        public virtual Category Category { get; set; }
        public virtual ICollection<Report> Reports { get; set; }
        public virtual ICollection<Comment> Comments { get; set; }
    }
}
