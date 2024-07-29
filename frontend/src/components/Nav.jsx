import { Box, Flex, Link, Stack } from "@chakra-ui/react";

const Nav = () => (
  <Box as="nav" bg="gray.800" color={"gray.300"} py={4} position="sticky" top="0" zIndex="1000" backdropFilter="blur(10px)">
    <Flex justify="center">
      <Stack direction="row" spacing={8}>
        <Link href="/builder" _hover={{ color: "purple.500" }} fontWeight="600">
          System Builder
        </Link>
        <Box className="dropdown" position="relative">
          <Link fontWeight="600">Browse Products</Link>
          <Box
            className="dropdown-content"
            display="none"
            position="absolute"
            bg="rgba(26, 26, 46, 0.95)"
            p={4}
            borderRadius="8px"
            boxShadow="0 8px 16px rgba(0, 0, 0, 0.2)"
            backdropFilter="blur(10px)"
            zIndex="1"
            left="50%"
            transform="translateX(-50%)"
          ></Box>
        </Box>
        <Link href="/builds" fontWeight="600">
          Completed Builds
        </Link>
        <Link href="/guide" fontWeight="600">
          Build Guides
        </Link>
      </Stack>
    </Flex>
  </Box>
);

export default Nav;
