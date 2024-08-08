import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.jsx";
import { ChakraProvider } from "@chakra-ui/react";
import { BrowserRouter } from "react-router-dom";
import theme from "./theme/theme";
import { PCBuildProvider } from "./components/PCBuildProvider.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ChakraProvider theme={theme}>
      <BrowserRouter>
        <PCBuildProvider>
          <App />
        </PCBuildProvider>
      </BrowserRouter>
    </ChakraProvider>
  </React.StrictMode>
);
