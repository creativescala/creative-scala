
function tocPatch() {
  const node = document.querySelector('ul.nav-list');

  // () => Array[Array[Element]]
  function makeChapters() {
    // Array[Array[Element]]
    //
    // Each Element is a TOC link. The first element is the chapter title
    const chapters = [];

    Array.from(node.children).forEach(item => {
      if (item.classList.contains('level1')) {
        chapters.push([item]);
      } else {
        const chapter = chapters.pop();
        chapter.push(item);
        chapters.push(chapter);
      }
    })

    return chapters;
  }

  // Element => Element
  //
  // Input should be of the form <li><a href="...">title</a></li>
  function makeChapterTitle(element) {
    const header = document.createElement("h5");
    header.replaceChildren(...element.children);

    return header;
  }

  // Element => Boolean
  function isActive(element) {
    return element.classList.contains('active');
  }

  // Array[Element] => Element
  function makeChapterToc(chapter) {
    const first = chapter.shift();
    const title = makeChapterTitle(first);

    if (first.classList.contains('nav-leaf')) {
      const li = document.createElement('li');
      li.className = first.className;
      li.appendChild(title);
      return li;
    } else {
      const children = [];

      const summary = document.createElement('summary');
      summary.appendChild(title);
      children.push(summary);

      const list = document.createElement('ul');
      list.replaceChildren(...chapter);
      children.push(list);

      const details = document.createElement('details');
      if (isActive(first) || chapter.some(isActive)) {
        details.setAttribute('open', 'true');
      }
      details.replaceChildren(...children);

      const li = document.createElement('li');
      li.className = first.className;
      li.appendChild(details);
      return li;
    }
  }

  function makeToc() {
    const chapters = makeChapters();
    console.log("chapters", chapters);
    const toc = chapters.map(chapter => makeChapterToc(chapter));

    node.replaceChildren(...toc);
  }

  makeToc();
}
addEventListener('load', () => tocPatch());
