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
        public DateTime DatePostArticle { get; set; }
        public string Title { get; set; }
        public string Brief { get; set; }
        public string  Content { get; set; } 
        public string Imagepath { get; set; }
        public int Status { get; set; }
        public int IdCategory { get; set; }
        [ForeignKey("IdCategory")]
        public virtual Category Category { get; set; }
        public virtual ICollection<Report> Reports { get; set; }
        public virtual ICollection<Comment> Comments { get; set; }
        public virtual IList<UserLikeArticles> UserLikeArticles { get; set; }
        public virtual IList<UserSaveArticles> UserSaveArticles { get; set; }
    }
}
