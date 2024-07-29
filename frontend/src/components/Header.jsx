import { Box, Heading, Text, useColorModeValue } from "@chakra-ui/react";

const Header = () => (
  <Box as="header" bg={"#6200EA"} color="white" py={4} textAlign="center">
    <Heading as="h1">sklopi.mk</Heading>
    <Text>Едноставно склопи го својот омилен компјутер</Text>
  </Box>
);

export default Header;
