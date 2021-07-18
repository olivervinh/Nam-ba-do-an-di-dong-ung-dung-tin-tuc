using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Models
{
    public class CommentViewModel
    {
        public int Id { get; set; }
        public int IdArticle { get; set; }
        public string Content { get; set; }
        public string UserName { get; set; }
    }
}
