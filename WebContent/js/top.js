window.addEventListener("load", () => {
    const loader = document.getElementById("loader");
    const loaderImg = loader.querySelector("img");
    const bgImage = document.querySelector(".bg-image");
    const bgOverlay = document.querySelector(".bg-overlay");
    const header = document.getElementById("header");
    const main = document.getElementById("main-content");
    const footer = document.getElementById("footer");

    // ローディング画像フェードイン
    loaderImg.style.opacity = 1;

    // ローディング後に黒背景をフェードアウト
    setTimeout(() => {
        loader.style.transition = "opacity 1s ease";
        loader.style.opacity = 0;

        setTimeout(() => {
            loader.style.display = "none";

            // 背景画像 & オーバーレイを表示してフェードイン
            [bgImage, bgOverlay].forEach(el => {
                el.style.opacity = 0;
                el.style.display = "block";
                setTimeout(() => el.style.opacity = 1, 50);
            });

            // ヘッダー、メイン、フッターを順にフェードイン
            setTimeout(() => {
                [header, main, footer].forEach(el => el.style.display = (el===header) ? "flex" : "block");
                setTimeout(() => header.style.opacity = 1, 0);
                setTimeout(() => main.style.opacity = 1, 200);
                setTimeout(() => footer.style.opacity = 1, 400);
            }, 500);

        }, 1000);
    }, 1500);
});
