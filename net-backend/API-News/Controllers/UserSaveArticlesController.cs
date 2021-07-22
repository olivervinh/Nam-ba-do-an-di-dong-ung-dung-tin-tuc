using API_News.Data;
using API_News.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserSaveArticlesController : ControllerBase
    {

        private readonly DPContext _context;
        public UserSaveArticlesController(DPContext context)
        {
            this._context = context;
        }

        [HttpPost("laydssave")]
        public async Task<ActionResult<IEnumerable<Article>>> GetAllArticles([FromForm] string id)
        {
            int idser = int.Parse(id);
            var kb = from l in _context.UserSaveArticles.Where(s => s.IdUser == idser)
                     join s in _context.Articles
                     on l.IdArticle equals s.Id
                     select new Article()
                     {
                         Id = s.Id,
                         Title = s.Title,
                         Imagepath = s.Imagepath,
                     };
            return await kb.ToListAsync();
        }
        [HttpPost]
        public async Task<ActionResult<UserSaveArticles>> Postasync([FromForm] string idUser, [FromForm] string idArticle, [FromForm] string status)
        {
            int article = int.Parse(idArticle);
            int user = int.Parse(idUser);
            int statuslike = int.Parse(status);
            UserSaveArticles userSaveArticles = new UserSaveArticles()
            {
                IdArticle = article,
                IdUser = user,
                StatusUserSave = statuslike,
            };
            _context.UserSaveArticles.Add(userSaveArticles);
            await _context.SaveChangesAsync();
            return Ok();
        }
        [HttpDelete]
        public async Task<ActionResult> DeleteAsync([FromForm] int idUser, [FromForm] int idArticle)
        {
            UserSaveArticles save = _context.UserSaveArticles.FirstOrDefault(s => s.IdArticle == idArticle && s.IdUser == idUser);
            _context.UserSaveArticles.Remove(save);
            await _context.SaveChangesAsync();
            return Ok();
        }
    }
}
