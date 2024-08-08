import React from "react";
import { Box } from "@chakra-ui/react";
import ComponentSlot from "./ComponentSlot";
import ComponentList from "./ComponentList";
import { usePCBuild } from "./PCBuildProvider";

const Builder = () => {
  const { pcBuild } = usePCBuild();

  return (
    <Box display="flex" flexDirection="column" justifyContent="center" alignItems="center" mb={8}>
      <Box position="relative" width="600px" height="600px">
        <ComponentSlot label="CPU" position={{ top: "0", left: "260px" }} product={pcBuild.cpu} />
        <ComponentSlot label="CPU Cooler" position={{ top: "0px", left: "160px" }} product={pcBuild.cpucooler} />
        <ComponentSlot label="Motherboard" position={{ top: "260px", left: "0px" }} product={pcBuild.motherboard} />

        {/* RAM Slots */}
        {pcBuild.ram?.map((ram, index) => (
          <ComponentSlot key={index} label={`Memory ${index + 1}`} position={{ top: `${260 + index * 100}px`, right: "0px" }} product={ram} />
        ))}

        {/* Storage Slots */}
        {pcBuild.storage?.map((storage, index) => (
          <ComponentSlot key={index} label={`Storage ${index + 1}`} position={{ top: "0px", left: `${360 + index * 100}px` }} product={storage} />
        ))}

        <ComponentSlot label="Video Card" position={{ top: "160px", right: "0px" }} product={pcBuild.gpu} />
        <ComponentSlot label="Power Supply" position={{ top: "360px", left: "0px" }} product={pcBuild.psu} />
        <ComponentSlot label="Case" position={{ top: "360px", right: "0px" }} product={pcBuild.pcCase} />

        {/* Case illustration box */}
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
};

export default Builder;
