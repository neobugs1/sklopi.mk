import { Box } from "@chakra-ui/react";
import ComponentSlot from "./ComponentSlot";
import ComponentList from "./ComponentList";

const Builder = () => (
  <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" mb={8}>
    <Box position="relative" width="600px" height="600px">
      <ComponentSlot label="CPU" position={{ top: "0", left: "260px" }} />
      <ComponentSlot label="CPU Cooler" position={{ top: "0px", left: "160px" }} />
      <ComponentSlot label="Motherboard" position={{ top: "260px", left: "0px" }} />
      <ComponentSlot label="Memory" position={{ top: "260px", right: "0px" }} />
      <ComponentSlot label="Storage" position={{ top: "0px", left: "360px" }} />
      <ComponentSlot label="Video Card" position={{ top: "160px", right: "0px" }} />
      <ComponentSlot label="Power Supply" position={{ top: "360px", left: "0px" }} />
      <ComponentSlot label="Case" position={{ top: "360px", right: "0px" }} />
      <Box
        width="300px"
        height="300px"
        bg="#333"
        borderRadius="10px"
        position="absolute"
        left="150px"
        top="150px"
        boxShadow="0 0 20px rgba(0, 0, 0, 0.5)"
        _before={{
          content: '""',
          position: "absolute",
          top: "10px",
          left: "10px",
          right: "10px",
          bottom: "10px",
          bg: "#222",
          borderRadius: "5px",
        }}
        _after={{
          content: '""',
          position: "absolute",
          top: "20px",
          left: "20px",
          right: "20px",
          height: "200px",
          bg: "#111",
          borderRadius: "5px",
        }}
      />
    </Box>
    <ComponentList />
  </Box>
);

export default Builder;
