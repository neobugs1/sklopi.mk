import { Box, Table, Thead, Tbody, Tr, Th, Td, Button } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { usePCBuild } from "./PCBuildProvider";

const ComponentList = () => {
  const navigate = useNavigate();
  const { pcBuild } = usePCBuild();

  const handleAddClick = (component) => {
    switch (component) {
      case "CPU":
        navigate("/cpu/products");
        break;
      case "CPU Cooler":
        navigate("/cpu-coolers/products");
        break;
      case "Motherboard":
        navigate("/motherboards/products");
        break;
      case "Memory":
        navigate("/ram/products");
        break;
      case "Storage":
        navigate("/storage/products");
        break;
      case "Video Card":
        navigate("/gpu/products");
        break;
      case "Power Supply":
        navigate("/psu/products");
        break;
      case "Case":
        navigate("/case/products");
        break;
      default:
        break;
    }
  };

  const components = [
    { label: "CPU", product: pcBuild.cpu },
    { label: "CPU Cooler", product: pcBuild.cpucooler },
    { label: "Motherboard", product: pcBuild.motherboard },
    { label: "Video Card", product: pcBuild.gpu },
    { label: "Power Supply", product: pcBuild.psu },
    { label: "Case", product: pcBuild.pcCase },
    ...(pcBuild.ram ? pcBuild.ram.map((ram, index) => ({ label: `Memory ${index + 1}`, product: ram })) : []),
    ...(pcBuild.storage ? pcBuild.storage.map((storage, index) => ({ label: `Storage ${index + 1}`, product: storage })) : []),
  ];

  return (
    <Box bg="rgba(255, 255, 255, 0.05)" borderRadius="10px" p={4}>
      <Table variant="simple">
        <Thead>
          <Tr>
            <Th bg="purple.700" color="white">
              Component
            </Th>
            <Th bg="purple.700" color="white">
              Selection
            </Th>
            <Th bg="purple.700" color="white">
              Price
            </Th>
            <Th bg="purple.700" color="white">
              Action
            </Th>
          </Tr>
        </Thead>
        <Tbody>
          {components.map(({ label, product }, index) => (
            <Tr key={index}>
              <Td>{label}</Td>
              <Td>{product ? product.name : "Not Selected"}</Td>
              <Td>{product ? `${product.price} ден.` : "-"}</Td>
              <Td>
                <Button bg="purple.400" color="white" _hover={{ bg: "purple.500" }} onClick={() => handleAddClick(label.split(" ")[0])}>
                  {product ? "Change" : "Add"}
                </Button>
              </Td>
            </Tr>
          ))}
        </Tbody>
      </Table>
    </Box>
  );
};

export default ComponentList;
