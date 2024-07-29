import { extendTheme } from "@chakra-ui/react";

const theme = extendTheme({
  colors: {
    primary: "#6200EA",
    secondary: "#B388FF",
    background: "#1A1A2E",
    text: "#E0E0E0",
    hover: "#7C4DFF",
  },
  fonts: {
    body: "Poppins, sans-serif",
    heading: "Poppins, sans-serif",
  },
  styles: {
    global: {
      body: {
        bg: "background",
        color: "text",
        margin: 0,
        padding: 0,
      },
    },
  },
  transition: {
    property: {
      common: "background-color, border-color, color, fill, stroke, opacity, box-shadow, transform",
    },
    duration: {
      fast: "200ms",
    },
    easing: {
      easeOut: "ease-out",
    },
  },
});

export default theme;
