using Microsoft.EntityFrameworkCore.Migrations;

namespace API_News.Migrations
{
    public partial class v7 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "LikeArticle",
                table: "Articles");

            migrationBuilder.DropColumn(
                name: "SaveArticle",
                table: "Articles");

            migrationBuilder.CreateTable(
                name: "UserLikeArticles",
                columns: table => new
                {
                    IdUser = table.Column<int>(type: "int", nullable: false),
                    IdArticle = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserLikeArticles", x => new { x.IdArticle, x.IdUser });
                    table.ForeignKey(
                        name: "FK_UserLikeArticles_Articles_IdArticle",
                        column: x => x.IdArticle,
                        principalTable: "Articles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserLikeArticles_Users_IdUser",
                        column: x => x.IdUser,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateTable(
                name: "UserSaveArticles",
                columns: table => new
                {
                    IdUser = table.Column<int>(type: "int", nullable: false),
                    IdArticle = table.Column<int>(type: "int", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_UserSaveArticles", x => new { x.IdArticle, x.IdUser });
                    table.ForeignKey(
                        name: "FK_UserSaveArticles_Articles_IdArticle",
                        column: x => x.IdArticle,
                        principalTable: "Articles",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_UserSaveArticles_Users_IdUser",
                        column: x => x.IdUser,
                        principalTable: "Users",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_UserLikeArticles_IdUser",
                table: "UserLikeArticles",
                column: "IdUser");

            migrationBuilder.CreateIndex(
                name: "IX_UserSaveArticles_IdUser",
                table: "UserSaveArticles",
                column: "IdUser");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "UserLikeArticles");

            migrationBuilder.DropTable(
                name: "UserSaveArticles");

            migrationBuilder.AddColumn<int>(
                name: "LikeArticle",
                table: "Articles",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "SaveArticle",
                table: "Articles",
                type: "int",
                nullable: false,
                defaultValue: 0);
        }
    }
}
