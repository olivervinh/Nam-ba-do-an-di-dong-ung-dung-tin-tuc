IF OBJECT_ID(N'[__EFMigrationsHistory]') IS NULL
BEGIN
    CREATE TABLE [__EFMigrationsHistory] (
        [MigrationId] nvarchar(150) NOT NULL,
        [ProductVersion] nvarchar(32) NOT NULL,
        CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY ([MigrationId])
    );
END;
GO

BEGIN TRANSACTION;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [Categories] (
        [Id] int NOT NULL IDENTITY,
        [Name] nvarchar(max) NULL,
        CONSTRAINT [PK_Categories] PRIMARY KEY ([Id])
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [ImageSlides] (
        [Id] int NOT NULL IDENTITY,
        [Url] nvarchar(max) NULL,
        CONSTRAINT [PK_ImageSlides] PRIMARY KEY ([Id])
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [Users] (
        [Id] int NOT NULL IDENTITY,
        [Fullname] nvarchar(max) NULL,
        [Username] nvarchar(max) NULL,
        [Password] nvarchar(100) NULL,
        CONSTRAINT [PK_Users] PRIMARY KEY ([Id])
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [Articles] (
        [Id] int NOT NULL IDENTITY,
        [DatePostArticle] datetime2 NOT NULL,
        [Title] nvarchar(max) NULL,
        [Brief] nvarchar(max) NULL,
        [Content] nvarchar(max) NULL,
        [Imagepath] nvarchar(max) NULL,
        [Status] int NOT NULL,
        [IdCategory] int NOT NULL,
        CONSTRAINT [PK_Articles] PRIMARY KEY ([Id]),
        CONSTRAINT [FK_Articles_Categories_IdCategory] FOREIGN KEY ([IdCategory]) REFERENCES [Categories] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [Comments] (
        [Id] int NOT NULL IDENTITY,
        [Content] nvarchar(max) NULL,
        [IdUser] int NOT NULL,
        [IdArticle] int NOT NULL,
        CONSTRAINT [PK_Comments] PRIMARY KEY ([Id]),
        CONSTRAINT [FK_Comments_Articles_IdArticle] FOREIGN KEY ([IdArticle]) REFERENCES [Articles] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_Comments_Users_IdUser] FOREIGN KEY ([IdUser]) REFERENCES [Users] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [Reports] (
        [Id] int NOT NULL IDENTITY,
        [Content] nvarchar(max) NULL,
        [IdUser] int NOT NULL,
        [IdArticle] int NOT NULL,
        CONSTRAINT [PK_Reports] PRIMARY KEY ([Id]),
        CONSTRAINT [FK_Reports_Articles_IdArticle] FOREIGN KEY ([IdArticle]) REFERENCES [Articles] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_Reports_Users_IdUser] FOREIGN KEY ([IdUser]) REFERENCES [Users] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [UserLikeArticles] (
        [IdUser] int NOT NULL,
        [IdArticle] int NOT NULL,
        [StatusUserLike] int NOT NULL,
        CONSTRAINT [PK_UserLikeArticles] PRIMARY KEY ([IdArticle], [IdUser]),
        CONSTRAINT [FK_UserLikeArticles_Articles_IdArticle] FOREIGN KEY ([IdArticle]) REFERENCES [Articles] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_UserLikeArticles_Users_IdUser] FOREIGN KEY ([IdUser]) REFERENCES [Users] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE TABLE [UserSaveArticles] (
        [IdUser] int NOT NULL,
        [IdArticle] int NOT NULL,
        [StatusUserSave] int NOT NULL,
        CONSTRAINT [PK_UserSaveArticles] PRIMARY KEY ([IdArticle], [IdUser]),
        CONSTRAINT [FK_UserSaveArticles_Articles_IdArticle] FOREIGN KEY ([IdArticle]) REFERENCES [Articles] ([Id]) ON DELETE CASCADE,
        CONSTRAINT [FK_UserSaveArticles_Users_IdUser] FOREIGN KEY ([IdUser]) REFERENCES [Users] ([Id]) ON DELETE CASCADE
    );
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_Articles_IdCategory] ON [Articles] ([IdCategory]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_Comments_IdArticle] ON [Comments] ([IdArticle]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_Comments_IdUser] ON [Comments] ([IdUser]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_Reports_IdArticle] ON [Reports] ([IdArticle]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_Reports_IdUser] ON [Reports] ([IdUser]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_UserLikeArticles_IdUser] ON [UserLikeArticles] ([IdUser]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    CREATE INDEX [IX_UserSaveArticles_IdUser] ON [UserSaveArticles] ([IdUser]);
END;
GO

IF NOT EXISTS(SELECT * FROM [__EFMigrationsHistory] WHERE [MigrationId] = N'20210721211702_v1')
BEGIN
    INSERT INTO [__EFMigrationsHistory] ([MigrationId], [ProductVersion])
    VALUES (N'20210721211702_v1', N'5.0.6');
END;
GO

COMMIT;
GO

