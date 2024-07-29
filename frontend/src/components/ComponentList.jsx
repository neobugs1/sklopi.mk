import { Box, Table, Thead, Tbody, Tr, Th, Td, Button } from "@chakra-ui/react";

const components = ["CPU", "CPU Cooler", "Motherboard", "Memory", "Storage", "Video Card", "Power Supply", "Case"];

const ComponentList = () => (
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
        {components.map((component) => (
          <Tr key={component}>
            <Td>{component}</Td>
            <Td>Not Selected</Td>
            <Td>-</Td>
            <Td>
              <Button bg="purple.400" color="white" _hover={{ bg: "purple.500" }}>
                Add
              </Button>
            </Td>
          </Tr>
        ))}
      </Tbody>
    </Table>
  </Box>
);

export default ComponentList;
