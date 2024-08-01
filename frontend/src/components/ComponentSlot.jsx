import { Box } from "@chakra-ui/react";

const ComponentSlot = ({ label, position }) => (
  <Box
    width="80px"
    height="80px"
    bg="rgba(255, 255, 255, 0.1)"
    border="2px solid rgba(255, 255, 255, 0.3)"
    borderRadius="10px"
    position="absolute"
    display="flex"
    justifyContent="center"
    alignItems="center"
    fontSize="0.8rem"
    fontWeight="bold"
    textAlign="center"
    cursor="pointer"
    transition="all 0.3s ease"
    _hover={{ bg: "purple.400", transform: "scale(1.05)" }}
    {...position}
  >
    {label}
  </Box>
);

export default ComponentSlot;