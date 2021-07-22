using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API_News.Data;
using API_News.Models;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ArticlesController : ControllerBase
    {
        private readonly DPContext _context;

        public ArticlesController(DPContext context)
        {
            _context = context;
        }
        [HttpGet("laytheoloai/{id}")]
        public async Task<ActionResult<IEnumerable<ArticleCategoryViewModel>>> GetArticlesCategory(int id)
        {
            var kb = from a in _context.Articles.Where(s=>s.IdCategory==id)
                     join c in _context.Categories
                     on a.IdCategory equals c.Id
                     select new ArticleCategoryViewModel()
                     {
                         NameCategory = c.Name,
                         Id = a.Id,
                         Brief = a.Brief,
                         Content = a.Content,
                         DatePostArticle = a.DatePostArticle,
                         Imagepath = a.Imagepath,
                         Status = a.Status,
                         Title = a.Title,
                     };
            return await kb.Where(s => s.Status == 1).ToListAsync();
        }
        // GET: api/Articles
        [HttpGet("laytheodieukien")]
        public async Task<ActionResult<IEnumerable<ArticleCategoryViewModel>>> GetArticlesWhere()
        {
            var kb = from a in _context.Articles.Where(s => s.Status == 1)
                     join c in _context.Categories
                     on a.IdCategory equals c.Id
                     select new ArticleCategoryViewModel()
                     {
                         NameCategory = c.Name,
                         Id = a.Id,
                         Brief = a.Brief,
                         Content = a.Content,
                         DatePostArticle = a.DatePostArticle,
                         Imagepath = a.Imagepath,
                         Status = a.Status,
                         Title = a.Title,
                     };
            return await kb.ToListAsync();
        }
        // GET: api/Articles
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Article>>> GetArticles()
        {
            return await _context.Articles.ToListAsync();
        }
        [HttpPost("search")]
        public async Task<ActionResult<ICollection<ArticleCategoryViewModel>>> SearchArticleAsync([FromForm]string search)
        {
            var kb = from a in _context.Articles.Where(s => s.Status == 1||  s.Content.Contains(search) || s.Title.Contains(search)||s.Brief.Contains(search))
                     join c in _context.Categories
                     on a.IdCategory equals c.Id
                     select new ArticleCategoryViewModel()
                     {
                         NameCategory = c.Name,
                         Id = a.Id,
                         Brief = a.Brief,
                         Content = a.Content,
                         DatePostArticle = a.DatePostArticle,
                         Imagepath = a.Imagepath,
                         Status = a.Status,
                         Title = a.Title,
                     };
         
            return await kb.ToListAsync();
        }

       // GET: api/Articles/5
       [HttpGet("{id}")]
        public async Task<ActionResult<ArticleCategoryViewModel>> GetArticle(int id)
        {
            var kb = from a in _context.Articles
                     join c in _context.Categories
                     on a.IdCategory equals c.Id
                     select new ArticleCategoryViewModel()
                     {
                         NameCategory = c.Name,
                         Id = a.Id,
                         Brief = a.Brief,
                         Content = a.Content,
                         DatePostArticle = a.DatePostArticle,
                         Imagepath = a.Imagepath,
                         Status = a.Status,
                         Title = a.Title,
                     };
            return await kb.FirstOrDefaultAsync(s=>s.Id==id);
        }

    
        // POST: api/Articles
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Article>> PostArticle(Article article)
        {
            _context.Articles.Add(article);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetArticle", new { id = article.Id }, article);
        }

        // DELETE: api/Articles/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteArticle(int id)
        {
            var article = await _context.Articles.FindAsync(id);
            if (article == null)
            {
                return NotFound();
            }

            _context.Articles.Remove(article);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ArticleExists(int id)
        {
            return _context.Articles.Any(e => e.Id == id);
        }

        [HttpGet("tintuclienquan1/{id}")]
        public async Task<ActionResult<IEnumerable<ArticleCategoryViewModel>>> tintuclienquan1(int id)
        {
            //lay ra tin tuc cung loai
            Article articleGetIdACate = await _context.Articles.FindAsync(id);
            var kb = from a in _context.Articles
                     join c in _context.Categories.Where(s=>s.Id== articleGetIdACate.IdCategory)
                     on a.IdCategory equals c.Id
                     select new ArticleCategoryViewModel()
                     {
                         NameCategory = c.Name,
                         Id = a.Id,
                         Brief = a.Brief,
                         Content = a.Content,
                         DatePostArticle = a.DatePostArticle,
                         Imagepath = a.Imagepath,
                         Status = a.Status,
                         Title = a.Title,
                     };
            return await kb.Where(s=>s.Id!=id).ToListAsync();
        }
      

    }
}
