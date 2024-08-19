import type { SocialObjects } from "@/lib/types";

export const SITE = {
  website: "https://cloudeko.github.io/cloudeko-zenei/",
  author: "HYP3R00T and Zenei contributors",
  desc: "Documentation for Cloudeko Zenei authentication and authorization service",
  title: "Cloudeko Zenei Docs",
  ogImage: "og-image.jpg",
  repo: "https://github.com/Cloudeko/cloudeko-zenei",
};

export const LOCALE = {
  lang: "en", // html lang code. Set this empty and default will be "en"
  langTag: ["en-EN"], // BCP 47 Language Tags. Set this empty [] to use the environment default
} as const;

export const menu_items: { title: string; href: string }[] = [
  // {
  //   title: "Home",
  //   href: "/",
  // },
];

// Just works with top-level folders and files. For files, don't add extension as it looks for the slug, and not the file name.
export const side_nav_menu_order: string[] = [
  "getting-started",
];

// Don't delete anything. You can use 'true' or 'false'.
// These are global settings
export const docconfig = {
  hide_table_of_contents: false,
  hide_breadcrumbs: false,
  hide_side_navigations: false,
  hide_datetime: false,
  hide_time: true,
  hide_search: false,
  hide_repo_button: false,
  hide_author: true,
};

// Set your social. It will appear in footer. Don't change the `name` value.
export const Socials: SocialObjects = [
  {
    name: "Github",
    href: "https://github.com/Cloudeko/cloudeko-zenei",
    linkTitle: ` ${SITE.title} on Github`,
    active: true,
  }
];
