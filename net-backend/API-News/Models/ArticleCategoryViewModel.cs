using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Models
{
    public class ArticleCategoryViewModel
    {
        public int Id { get; set; }
        public DateTime DatePostArticle { get; set; }
        public string Title { get; set; }
        public string Brief { get; set; }
        public string Content { get; set; }
        public string Imagepath { get; set; }
        public int Status { get; set; }
        public string NameCategory { get; set; }
    }
}
