using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Models
{
    public class Report
    {
        public int Id { get; set; }
        public string Content { get; set; }
        public int IdUser { get; set; }
        [ForeignKey("IdUser")]
        public virtual User User { get; set; }
        public int IdArticle { get; set; }
        [ForeignKey("IdArticle")]
        public virtual Article Article { get; set; }

    }
}
