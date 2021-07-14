using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using API_News.Models;
using Microsoft.EntityFrameworkCore;
namespace API_News.Data
{
    public class DPContext: DbContext
    {
        public DPContext(DbContextOptions options):base(options)
        {
            
        }
        public virtual DbSet<Article> Articles { get; set; }
        public virtual DbSet<Category> Categories { get; set; }

        public virtual DbSet<Comment> Comments { get; set; }
        public virtual DbSet<Report> Reports { get; set; }
        public virtual DbSet<User> Users { get; set; }

    }
}
