document.addEventListener("DOMContentLoaded", () => {
    const sections = document.querySelectorAll("section, .accueil");
    const overlay = document.querySelector('.elementor-background-overlay');
    if (overlay) {
        const height = overlay.offsetHeight; // Obtenir la hauteur de l'élément
        document.documentElement.style.setProperty('--overlay-height', `${height}px`);
    }
    const manageSectionVisibility = (entry) => {
        const section = entry.target;

        if (entry.isIntersecting) {
            section.style.visibility = "visible";
            section.style.opacity = "1";
            const videos = section.querySelectorAll("video");
            videos.forEach(video => {
                if (!video.dataset.loaded) {
                    const source = video.querySelector("source");
                    if (source && source.dataset.src) {
                        source.src = source.dataset.src;
                        video.load();
                        video.play();
                        video.dataset.loaded = "true";
                    }
                } else {
                    video.play();
                }
            });
            const images = section.querySelectorAll("img");
            images.forEach(img => {
                if (img.dataset.src) {
                    img.src = img.dataset.src;
                    img.removeAttribute("data-src");
                }
            });
        } else {
            section.style.visibility = "hidden";
            section.style.opacity = "0";
            const videos = section.querySelectorAll("video");
            videos.forEach(video => {
                if (!video.paused) {
                    video.pause();
                }
            });
        }
    };
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(manageSectionVisibility);
    }, {
        threshold: 0.1,
    });
    sections.forEach((section) => {
        observer.observe(section);
    });
    sections.forEach(section => {
        section.style.visibility = "hidden";
        section.style.opacity = "0";
        const videos = section.querySelectorAll("video");
        videos.forEach(video => {
            if (!video.paused) {
                video.pause(); 
            }
            video.dataset.loaded = "";
        });
        const images = section.querySelectorAll("img");
        images.forEach(img => {
            if (!img.dataset.src) {
                img.dataset.src = img.src;
                img.src = "";
            }
        });
    });
});
