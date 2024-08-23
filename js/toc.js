// Reformats table of contents so it can be styled as we desire
function tocPatch() {
  // All the TOCs (there should be two, for mobile and desktop.)
  const nodes = document.querySelectorAll('ul.nav-list');

  // Node => Array[Array[Element]]
  //
  // Collect the chapters into a data structure
  function makeChapters(node) {
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
    nodes.forEach(node => {
      const chapters = makeChapters(node);
      // console.log("chapters", chapters);
      const toc = chapters.map(chapter => makeChapterToc(chapter));

      node.replaceChildren(...toc);
    });
  }

  makeToc();
}
// Run it when the page loads
addEventListener('load', () => tocPatch());
